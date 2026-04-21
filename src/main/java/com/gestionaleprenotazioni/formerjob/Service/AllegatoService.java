package com.gestionaleprenotazioni.formerjob.Service;

import com.gestionaleprenotazioni.formerjob.Dto.EventDto;
import com.gestionaleprenotazioni.formerjob.Dto.TicketDto;
import com.gestionaleprenotazioni.formerjob.Dto.UserDto;
import org.springframework.stereotype.Service;

@Service
public class AllegatoService {

    private final PdfService pdfService;
    private final EmailService emailService;

    public AllegatoService(PdfService pdfService, EmailService emailService) {
        this.pdfService = pdfService;
        this.emailService = emailService;
    }

    public void inviaOrdineConAllegato(TicketDto ticketDto,EventDto eventDto,UserDto userDto) {

        byte[] pdf = pdfService.generaPdfOrdine(ticketDto.getName(),ticketDto.getSurname(),ticketDto.getPrice(),eventDto.getLocation(),eventDto.getName(),eventDto.getDate());

        String html = """
                <h2>Conferma ordine</h2>
                <p>In allegato trovi il riepilogo PDF del tuo ordine.</p>
                """;

        emailService.sendEmailWithTicket(
                userDto.getEmail(),
                "Conferma dell' ordine  da EventIO",
                html,
                pdf,
                "Ricevuta pdf biglietto"
        );
    }
}