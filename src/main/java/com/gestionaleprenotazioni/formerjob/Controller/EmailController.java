package com.gestionaleprenotazioni.formerjob.Controller;

import com.gestionaleprenotazioni.formerjob.Dto.EventDto;
import com.gestionaleprenotazioni.formerjob.Dto.PlaceDto;
import com.gestionaleprenotazioni.formerjob.Dto.TicketDto;
import com.gestionaleprenotazioni.formerjob.Dto.UserDto;
import com.gestionaleprenotazioni.formerjob.Model.Ticket;
import com.gestionaleprenotazioni.formerjob.Service.EmailService;
import com.gestionaleprenotazioni.formerjob.Service.EventService;
import com.gestionaleprenotazioni.formerjob.Service.PlaceService;
import com.gestionaleprenotazioni.formerjob.Service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/email")
public class EmailController {

    private final EmailService emailService;
    private final PlaceService placeService;
    private final EventService eventService;
    private final UserService userService;

    public EmailController(EmailService emailService, PlaceService placeService, EventService eventService, UserService userService) {
        this.emailService = emailService;
        this.eventService = eventService;
        this.placeService = placeService;
        this.userService = userService;
    }

    @GetMapping("/send-test-email")
    public String sendTestEmail(@RequestParam String to, @RequestParam TicketDto ticketDto) {

        PlaceDto placeDto = placeService.read(ticketDto.getPlaceId());
        EventDto eventDto = eventService.read(ticketDto.getEventId());
        UserDto userDto = userService.read(ticketDto.getUserId());

        String subject = "Mail automatica";
        String body = """
                Ciao,  da EventIO!
                Hai Completato l'acquisto del tuo biglietto per l'evento event_name
                che si terrà il giorno event_date al ticket_placeName.
                
                Grazie per aver scelto EventIO, ti aspettiamo all'evento!
             
                """.formatted(LocalDateTime.now());

        try {
            emailService.sendSimpleEmail(to, subject, body);
            return "✅ Email di prova inviata (controlla la inbox Mailtrap)";
        } catch (Exception e) {
            // Qui puoi anche loggare l'eccezione con un logger
            return "❌ Errore durante l'invio: " + e.getMessage();
        }
    }
}