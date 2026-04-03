package com.gestionaleprenotazioni.formerjob.Controller;

import com.gestionaleprenotazioni.formerjob.Dto.EventDto;
import com.gestionaleprenotazioni.formerjob.Dto.TicketDto;
import com.gestionaleprenotazioni.formerjob.Dto.UserDto;
import com.gestionaleprenotazioni.formerjob.Service.AllegatoService;
import com.gestionaleprenotazioni.formerjob.Service.EmailService;
import com.gestionaleprenotazioni.formerjob.Service.EventService;
import com.gestionaleprenotazioni.formerjob.Service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/email")
public class EmailController {

    private final EmailService emailService;
    private final EventService eventService;
    private final UserService userService;
    private final AllegatoService allegatoService;

    public EmailController(EmailService emailService, EventService eventService, UserService userService,AllegatoService allegatoService) {
        this.emailService = emailService;
        this.eventService = eventService;
        this.userService = userService;
        this.allegatoService = allegatoService;
    }

    @PostMapping("/send-test-email")
    public ResponseEntity<Map<String, String>> sendTestEmail(@RequestBody TicketDto ticketDto) {
        EventDto eventDto = eventService.read(ticketDto.getEventId());
        UserDto userDto = userService.read(ticketDto.getUserId());

        String subject = emailService.buildPurchaseConfirmationSubject(eventDto.getName());
        String body = emailService.buildPurchaseConfirmationBody(userDto, eventDto);

        emailService.sendSimpleEmail(userDto.getEmail(), subject, body);

        return ResponseEntity.ok(Map.of("message", "Email di conferma inviata con successo"));
    }

    @PostMapping("/invia-mail-pdf")
    public ResponseEntity<Map<String, String>> inviaMailPdf(@RequestParam Long ordineId,
                                                             @RequestParam String email) {
        try {
            allegatoService.inviaOrdineConAllegato(ordineId, email);
            return ResponseEntity.ok(Map.of("message", "Mail inviata con PDF allegato con successo"));
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Errore durante l'invio della mail: " + e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "Errore interno: " + e.getMessage()));
        }
    }
}