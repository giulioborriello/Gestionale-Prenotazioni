package com.gestionaleprenotazioni.formerjob.Controller;

import com.gestionaleprenotazioni.formerjob.Dto.TicketDto;
import com.gestionaleprenotazioni.formerjob.Model.Ticket;
import com.gestionaleprenotazioni.formerjob.Service.EmailService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/email")
public class EmailController {

    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @GetMapping("/send-test-email")
    public String sendTestEmail(TicketDto ticketDto,String receiver) {
        String to = "destinatario-di-prova@example.com"; // qualsiasi mail, non verrà usata realmente
        String subject = "Mail di prova da Spring + Mailtrap";
        String body = """
                Ciao,name_user da EventIO!
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