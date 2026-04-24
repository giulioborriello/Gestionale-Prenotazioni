package com.gestionaleprenotazioni.formerjob.Service;

import com.gestionaleprenotazioni.formerjob.Dto.EventDto;
import com.gestionaleprenotazioni.formerjob.Dto.UserDto;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
        return "Ciao " + userDto.getName() + " " + userDto.getSurname() + ",\n\n"
                + "grazie per il tuo acquisto su EventIO.\n"
                + "Il tuo biglietto e stato confermato con i seguenti dettagli:\n\n"
                + "Evento: " + eventDto.getName() + "\n"
                + "Data evento: " + formatEventDate(eventDto.getDate()) + "\n"
                + "Posto: " + eventDto.getLocation() + "\n"
                + "Ti aspettiamo!\n"
                + "Team EventIO";
    }

    public void sendSimpleEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
    }

    // Metodo originale — un solo allegato
    public void sendEmailWithTicket(String to, String subject, String body, byte[] pdfBytes, String linkedFile) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body);
            helper.addAttachment(linkedFile, new ByteArrayResource(pdfBytes), "application/pdf");
            mailSender.send(message);
        } catch (Exception e) {
            throw new IllegalStateException("Errore invio mail con allegato PDF", e);
        }
    }

    // Nuovo metodo — lista di allegati (Map<nomeFile, bytesFile>)
    public void sendEmailWithMultipleTickets(String to, String subject, String body, Map<String, byte[]> attachments) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body);

            for (Map.Entry<String, byte[]> entry : attachments.entrySet()) {
                helper.addAttachment(entry.getKey(), new ByteArrayResource(entry.getValue()), "application/pdf");
            }

            mailSender.send(message);
        } catch (Exception e) {
            throw new IllegalStateException("Errore invio mail con allegati PDF multipli", e);
        }
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