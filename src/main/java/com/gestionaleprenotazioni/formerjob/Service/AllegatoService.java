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

    /**
     * Invia ordine legacy con allegato PDF (hardcoded)
     * DEPRECATED: Usare inviaRicevutaPagamento() per dati reali
     */
    @Deprecated
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

    /**
     * Invia ricevuta di pagamento con allegato PDF (dati reali dal DB)
     * @param paymentId ID del pagamento
     * @param emailCliente Email del cliente
     */
    public void inviaRicevutaPagamento(Integer paymentId, String emailCliente) {
        try {
            byte[] pdf = pdfService.generaPdfPagamento(paymentId);

            String html = """
                    <h2>Ricevuta di Pagamento</h2>
                    <p>In allegato trovi la ricevuta del tuo pagamento.</p>
                    <p>Grazie per aver utilizzato i nostri servizi!</p>
                    """;

            emailService.sendEmailWithTicket(
                    emailCliente,
                    "Ricevuta Pagamento #" + paymentId,
                    html,
                    pdf,
                    "ricevuta-" + paymentId + ".pdf"
            );
        } catch (RuntimeException e) {
            throw new RuntimeException("Errore nell'invio della ricevuta: " + e.getMessage());
        }
    }

    /**
     * Invia biglietto con allegato PDF (dati reali dal DB)
     * @param ticketId ID del biglietto
     * @param emailCliente Email del cliente
     */
    public void inviaBiglietto(Integer ticketId, String emailCliente) {
        try {
            byte[] pdf = pdfService.generaPdfBiglietto(ticketId);

            String html = """
                    <h2>Il tuo Biglietto</h2>
                    <p>In allegato trovi il tuo biglietto per l'evento.</p>
                    <p>Conservalo e presentalo al momento dell'ingresso.</p>
                    """;

            emailService.sendEmailWithTicket(
                    emailCliente,
                    "Biglietto Evento",
                    html,
                    pdf,
                    "biglietto-" + ticketId + ".pdf"
            );
        } catch (RuntimeException e) {
            throw new RuntimeException("Errore nell'invio del biglietto: " + e.getMessage());
        }
    }
}