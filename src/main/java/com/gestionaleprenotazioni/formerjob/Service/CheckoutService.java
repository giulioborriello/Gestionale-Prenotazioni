package com.gestionaleprenotazioni.formerjob.Service;

import com.gestionaleprenotazioni.formerjob.Dto.PaymentDto;
import com.gestionaleprenotazioni.formerjob.Dto.TicketDto;
import com.gestionaleprenotazioni.formerjob.Exception.EventNotFoundException;
import com.gestionaleprenotazioni.formerjob.Exception.UserNotFoundException;
import com.gestionaleprenotazioni.formerjob.Mapper.EventMapper;
import com.gestionaleprenotazioni.formerjob.Mapper.UserMapper;
import com.gestionaleprenotazioni.formerjob.Repository.EventRepository;
import com.gestionaleprenotazioni.formerjob.Repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class CheckoutService {

    private final PaymentService paymentService;
    private final TicketService ticketService;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final PdfService pdfService;
    private final EmailService emailService;
    private final UserMapper userMapper;
    private final EventMapper eventMapper;

    // Constructor Injection: Spring inietta automaticamente le dipendenze
    public CheckoutService(PaymentService paymentService,
                           TicketService ticketService,
                           UserRepository userRepository,
                           EventRepository eventRepository,
                           PdfService pdfService,
                           EmailService emailService,
                           UserMapper userMapper,
                           EventMapper eventMapper) {
        this.paymentService = paymentService;
        this.ticketService = ticketService;
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
        this.pdfService = pdfService;
        this.emailService = emailService;
        this.userMapper = userMapper;
        this.eventMapper = eventMapper;
    }

    /**
     * Conclude l'acquisto: salva pagamento, crea ticket, genera PDF e invia email.
     */
    @Transactional
    public void completePurchase(PaymentDto paymentDto) {
        // Aggiungi questo log:
        System.out.println("DEBUG - Sto processando paymentDto: " + paymentDto);
        System.out.println("DEBUG - UserId arrivato: " + (paymentDto.getUserId() != null ? paymentDto.getUserId() : "NULL"));
        System.out.println("DEBUG - EventId arrivato: " + (paymentDto.getEventId() != null ? paymentDto.getEventId() : "NULL"));

        // 1. Recupero entità con gestione errori specifica
        var user = userRepository.findById(paymentDto.getUserId())
                .orElseThrow(() -> new UserNotFoundException("Utente con ID " + paymentDto.getUserId() + " non trovato"));

        var event = eventRepository.findById(paymentDto.getEventId())
                .orElseThrow(() -> new EventNotFoundException("Evento con ID " + paymentDto.getEventId() + " non trovato"));

        // 2. Salvataggio pagamento
        paymentService.insert(paymentDto);

        // 3. Creazione del Ticket
        TicketDto ticketDto = new TicketDto();
        ticketDto.setUserId(user.getId());
        ticketDto.setEventId(event.getId());
        ticketDto.setPrice(event.getTicketPrice());
        ticketDto.setName(user.getName());
        ticketDto.setSurname(user.getSurname());
        ticketDto.setCreationDate(LocalDateTime.now());

        ticketService.insert(ticketDto);

        // 4. Generazione PDF
        byte[] pdfContent = pdfService.generaPdfOrdine(
                user.getName(),
                user.getSurname(),
                event.getTicketPrice(),
                event.getLocation(),
                event.getName(),
                event.getDate()
        );

        // 5. Preparazione dati per Email
        var userDto = userMapper.toDTO(user);
        var eventDto = eventMapper.toDTO(event);

        String subject = emailService.buildPurchaseConfirmationSubject(event.getName());
        String body = emailService.buildPurchaseConfirmationBody(userDto, eventDto);
        String fileName = "Biglietto_" + event.getName().replace(" ", "_") + ".pdf";

        // 6. Invio Email
        emailService.sendEmailWithTicket(
                user.getEmail(),
                subject,
                body,
                pdfContent,
                fileName
        );
    }
}