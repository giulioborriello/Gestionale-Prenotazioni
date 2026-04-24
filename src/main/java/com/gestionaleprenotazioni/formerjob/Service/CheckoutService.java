package com.gestionaleprenotazioni.formerjob.Service;

import com.gestionaleprenotazioni.formerjob.Dto.PaymentDto;
import com.gestionaleprenotazioni.formerjob.Dto.TicketDto;
import com.gestionaleprenotazioni.formerjob.Dto.TicketHolderDto;
import com.gestionaleprenotazioni.formerjob.Exception.EventNotFoundException;
import com.gestionaleprenotazioni.formerjob.Exception.UserNotFoundException;
import com.gestionaleprenotazioni.formerjob.Mapper.EventMapper;
import com.gestionaleprenotazioni.formerjob.Mapper.UserMapper;
import com.gestionaleprenotazioni.formerjob.Repository.EventRepository;
import com.gestionaleprenotazioni.formerjob.Repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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

    @Transactional
    public void completePurchase(PaymentDto paymentDto) {

        // 1. Recupero entità
        var user = userRepository.findById(paymentDto.getUserId())
                .orElseThrow(() -> new UserNotFoundException("Utente con ID " + paymentDto.getUserId() + " non trovato"));

        var event = eventRepository.findById(paymentDto.getEventId())
                .orElseThrow(() -> new EventNotFoundException("Evento con ID " + paymentDto.getEventId() + " non trovato"));

        // 2. Salvataggio pagamento
        PaymentDto savedPayment = paymentService.insert(paymentDto);

        // 3. Determina la lista degli intestatari
        // Se il frontend manda la lista, la usa; altrimenti crea un ticket solo per l'utente
        List<TicketHolderDto> holders = (paymentDto.getTickets() != null && !paymentDto.getTickets().isEmpty())
                ? paymentDto.getTickets()
                : List.of(new TicketHolderDto(user.getName(), user.getSurname()));

        // 4. Per ogni intestatario: crea ticket + genera PDF
        Map<String, byte[]> attachments = new LinkedHashMap<>();
        int counter = 1;

        for (TicketHolderDto holder : holders) {
            // Salva il ticket
            TicketDto ticketDto = new TicketDto();
            ticketDto.setUserId(user.getId());
            ticketDto.setEventId(event.getId());
            ticketDto.setPrice(event.getTicketPrice());
            ticketDto.setName(holder.getName());
            ticketDto.setSurname(holder.getSurname());
            ticketDto.setCreationDate(LocalDateTime.now());
            ticketDto.setPaymentId(savedPayment.getId());
            ticketService.insert(ticketDto);

            // Genera PDF per questo intestatario
            byte[] pdfContent = pdfService.generaPdfOrdine(
                    holder.getName(),
                    holder.getSurname(),
                    event.getTicketPrice(),
                    event.getLocation(),
                    event.getName(),
                    event.getDate()
            );

            String fileName = "Biglietto_" + event.getName().replace(" ", "_") + "_" + counter + ".pdf";
            attachments.put(fileName, pdfContent);
            counter++;
        }

        // 5. Preparazione email
        var userDto = userMapper.toDTO(user);
        var eventDto = eventMapper.toDTO(event);

        String subject = emailService.buildPurchaseConfirmationSubject(event.getName());
        String body = emailService.buildPurchaseConfirmationBody(userDto, eventDto);

        // 6. Invio email con tutti gli allegati
        emailService.sendEmailWithMultipleTickets(user.getEmail(), subject, body, attachments);
    }
}