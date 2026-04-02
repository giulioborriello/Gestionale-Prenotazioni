package com.gestionaleprenotazioni.formerjob.Controller;

import com.gestionaleprenotazioni.formerjob.Dto.EventDto;
import com.gestionaleprenotazioni.formerjob.Dto.TicketDto;
import com.gestionaleprenotazioni.formerjob.Dto.UserDto;
import com.gestionaleprenotazioni.formerjob.Service.EmailService;
import com.gestionaleprenotazioni.formerjob.Service.EventService;
import com.gestionaleprenotazioni.formerjob.Service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/email")
public class EmailController {

    private final EmailService emailService;
    private final EventService eventService;
    private final UserService userService;

    public EmailController(EmailService emailService, EventService eventService, UserService userService) {
        this.emailService = emailService;
        this.eventService = eventService;
        this.userService = userService;
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
}