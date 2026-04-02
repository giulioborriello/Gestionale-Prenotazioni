package com.gestionaleprenotazioni.formerjob.Service;

import com.gestionaleprenotazioni.formerjob.Dto.EventDto;
import com.gestionaleprenotazioni.formerjob.Dto.UserDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Service
public class EmailService {

    private static final DateTimeFormatter EVENT_DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private final JavaMailSender mailSender;

    @Value("${app.mail.from}")
    private String from;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public String buildPurchaseConfirmationSubject(String eventName) {
        return "Conferma acquisto biglietto - " + eventName;
    }

    public String buildPurchaseConfirmationBody(UserDto userDto, EventDto eventDto) {
        return """
        <!DOCTYPE html>
        <html lang="it">
        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <meta name="x-apple-disable-message-reformatting">
            <title>Conferma acquisto - EventIO</title>
        </head>
        <body style="margin:0; padding:0; background-color:#020617; font-family:Arial, Helvetica, sans-serif; color:#e5e5e5;">

        <div style="display:none; max-height:0; overflow:hidden; opacity:0;">
            Il tuo acquisto su EventIO è stato confermato con successo.
        </div>

        <table role="presentation" width="100%" cellspacing="0" cellpadding="0" border="0"
               style="background-color:#020617; margin:0; padding:32px 12px;">
            <tr>
                <td align="center">
                    <table role="presentation" width="100%" cellspacing="0" cellpadding="0" border="0"
                           style="max-width:720px; background-color:#020617; border-radius:24px; border:1px solid #111827;">

                        <tr>
                            <td style="padding:28px 32px 12px 32px;">
                                <div style="font-size:24px; line-height:30px; font-weight:700; color:#ccff55; margin:0 0 6px 0;">
                                    EventIO
                                </div>
                                <div style="font-size:14px; line-height:22px; color:#9ca3af;">
                                    Conferma acquisto biglietto
                                </div>
                            </td>
                        </tr>

                        <tr>
                            <td style="padding:8px 32px 24px 32px;">
                                <p style="margin:0 0 14px 0; font-size:16px; line-height:26px; color:#f9fafb;">
                                    Ciao <span style="font-weight:700; color:#ccff55;">"""
                + userDto.getName() + " " + userDto.getSurname() + """
                                    </span>,
                                </p>

                                <p style="margin:0 0 8px 0; font-size:15px; line-height:24px; color:#e5e7eb;">
                                    grazie per il tuo acquisto su <span style="font-weight:700; color:#ccff55;">EventIO</span>.
                                </p>

                                <p style="margin:0; font-size:15px; line-height:24px; color:#e5e7eb;">
                                    Il tuo biglietto è stato confermato con i seguenti dettagli:
                                </p>
                            </td>
                        </tr>

                        <tr>
                            <td style="padding:0 32px 24px 32px;">
                                <table role="presentation" width="100%" cellspacing="0" cellpadding="0" border="0"
                                       style="background-color:#020617; border:1px solid #374151; border-radius:18px;">
                                    <tr>
                                        <td style="padding:20px 22px;">
                                            <p style="margin:0 0 6px 0; font-size:12px; line-height:18px;
                                                     text-transform:uppercase; letter-spacing:1.4px;
                                                     color:#9ca3af; font-weight:700;">
                                                Evento
                                            </p>

                                            <p style="margin:0 0 18px 0; font-size:22px; line-height:30px;
                                                     font-weight:700; color:#ccff55;">"""
                + eventDto.getName() + """
                                            </p>

                                            <table role="presentation" width="100%" cellspacing="0" cellpadding="0" border="0">
                                                <tr>
                                                    <td valign="top" style="width:50%; padding:0 8px 0 0;">
                                                        <table role="presentation" width="100%" cellspacing="0" cellpadding="0" border="0"
                                                               style="background-color:#020617; border-radius:14px; border:1px solid #374151;">
                                                            <tr>
                                                                <td style="padding:14px 14px 12px 14px;">
                                                                    <p style="margin:0 0 4px 0; font-size:11px; line-height:16px;
                                                                             text-transform:uppercase; letter-spacing:1px;
                                                                             color:#9ca3af; font-weight:700;">
                                                                        Data evento
                                                                    </p>

                                                                    <p style="margin:0; font-size:15px; line-height:22px; color:#f9fafb;">"""
                + formatEventDate(eventDto.getDate()) + """
                                                                    </p>
                                                                </td>
                                                            </tr>
                                                        </table>
                                                    </td>

                                                    <td valign="top" style="width:50%; padding:0 0 0 8px;">
                                                        <table role="presentation" width="100%" cellspacing="0" cellpadding="0" border="0"
                                                               style="background-color:#020617; border-radius:14px; border:1px solid #374151;">
                                                            <tr>
                                                                <td style="padding:14px 14px 12px 14px;">
                                                                    <p style="margin:0 0 4px 0; font-size:11px; line-height:16px;
                                                                             text-transform:uppercase; letter-spacing:1px;
                                                                             color:#9ca3af; font-weight:700;">
                                                                        Stato
                                                                    </p>

                                                                    <p style="margin:0; font-size:15px; line-height:22px;
                                                                             color:#ccff55; font-weight:700;">
                                                                        Confermato
                                                                    </p>
                                                                </td>
                                                            </tr>
                                                        </table>
                                                    </td>
                                                </tr>
                                            </table>

                                        </td>
                                    </tr>
                                </table>
                            </td>
                        </tr>

                        <tr>
                            <td style="padding:0 32px 24px 32px;">
                                <table role="presentation" width="100%" cellspacing="0" cellpadding="0" border="0"
                                       style="background-color:#020617; border-radius:16px; border:1px solid #374151;">
                                    <tr>
                                        <td style="padding:16px 18px; font-size:14px; line-height:22px; color:#e5e7eb;">
                                            Ti aspettiamo per vivere insieme una bellissima esperienza.
                                            Conserva questa email come conferma della tua prenotazione.
                                        </td>
                                    </tr>
                                </table>
                            </td>
                        </tr>

                        <tr>
                            <td style="padding:16px 32px 26px 32px; border-top:1px solid #111827;">
                                <p style="margin:0; font-size:13px; line-height:20px; color:#9ca3af;">
                                    Team <span style="color:#ccff55; font-weight:700;">EventIO</span>
                                </p>
                            </td>
                        </tr>

                    </table>
                </td>
            </tr>
        </table>

        </body>
        </html>
        """;
    }


    public void sendSimpleEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
    }

    private String formatEventDate(Date eventDate) {
        if (eventDate == null) {
            return "Data non disponibile";
        }
        return eventDate.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime()
                .format(EVENT_DATE_FORMATTER);
    }
}
