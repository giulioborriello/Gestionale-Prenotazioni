package com.gestionaleprenotazioni.formerjob.Service;

import org.springframework.stereotype.Service;

@Service
public class AllegatoService {

    private final PdfService pdfService;
    private final EmailService emailService;

    public AllegatoService(PdfService pdfService, EmailService emailService) {
        this.pdfService = pdfService;
        this.emailService = emailService;
    }

    public void inviaOrdineConAllegato(Long ordineId, String emailCliente) {
        String cliente = "Mario Rossi";
        double totale = 149.90;

        byte[] pdf = pdfService.generaPdfOrdine(ordineId, cliente, totale);

        String html = """
                <h2>Conferma ordine</h2>
                <p>In allegato trovi il riepilogo PDF del tuo ordine.</p>
                """;

        emailService.sendEmailWithTicket(
                emailCliente,
                "Ordine #" + ordineId,
                html,
                pdf,
                "ordine-" + ordineId + ".pdf"
        );
    }
}