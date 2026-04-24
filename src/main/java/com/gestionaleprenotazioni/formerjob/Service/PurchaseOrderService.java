package com.gestionaleprenotazioni.formerjob.Service;

import com.gestionaleprenotazioni.formerjob.Dto.EventDto;
import com.gestionaleprenotazioni.formerjob.Dto.PurchaseOrderDto;
import com.gestionaleprenotazioni.formerjob.Dto.TicketDto;
import com.gestionaleprenotazioni.formerjob.Dto.UserDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service per la gestione degli ordini di acquisto.
 * <p>
 * Questo servizio coordina il salvataggio dei pagamenti, la creazione dei biglietti
 * e l'invio delle email di conferma con i biglietti in formato PDF.
 * </p>
 */
@Service
public class PurchaseOrderService {

    private final PaymentService paymentService;
    private final TicketService ticketService;
    private final UserService userService;
    private final EventService eventService;
    private final PdfService pdfService;
    private final EmailService emailService;

    public PurchaseOrderService(PaymentService paymentService, TicketService ticketService,
                                UserService userService, EventService eventService,
                                PdfService pdfService, EmailService emailService) {
        this.paymentService = paymentService;
        this.ticketService = ticketService;
        this.userService = userService;
        this.eventService = eventService;
        this.pdfService = pdfService;
        this.emailService = emailService;
    }

    /**
     * Elabora un ordine di acquisto.
     */
    @Transactional
    public void processOrder(PurchaseOrderDto orderDto) {
        // 1. Salva il pagamento e CATTURA l'oggetto salvato (che avrà l'ID)
        // Assicurati che paymentService.insert() ritorni l'oggetto salvato (o DTO) con l'ID popolato
        var savedPayment = (orderDto.getPayment() != null)
                ? paymentService.insert(orderDto.getPayment())
                : null;

        // 2. Itera sulla lista dei biglietti
        if (orderDto.getTickets() != null && savedPayment != null) {
            for (TicketDto ticketDto : orderDto.getTickets()) {

                // *** AGGIUNTA FONDAMENTALE ***
                // Colleghiamo il ticket al pagamento appena salvato
                ticketDto.setPaymentId(savedPayment.getId());

                // Salvataggio del ticket (ora il database sa che appartiene a quel pagamento)
                TicketDto savedTicket = ticketService.insert(ticketDto);

                // Recupero dei dati necessari per PDF ed Email
                UserDto user = userService.read(savedTicket.getUserId());
                EventDto event = eventService.read(savedTicket.getEventId());

                // Generazione PDF
                byte[] pdfBytes = pdfService.generaPdfOrdine(
                        savedTicket.getName(),
                        savedTicket.getSurname(),
                        savedTicket.getPrice(),
                        event.getLocation(),
                        event.getName(),
                        event.getDate()
                );

                // Invio Email
                String subject = emailService.buildPurchaseConfirmationSubject(event.getName());
                String body = emailService.buildPurchaseConfirmationBody(user, event);
                String fileName = "Biglietto_" + event.getName().replace(" ", "_") + ".pdf";

                emailService.sendEmailWithTicket(user.getEmail(), subject, body, pdfBytes, fileName);
            }
        }
    }
}