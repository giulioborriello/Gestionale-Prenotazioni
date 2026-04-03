package com.gestionaleprenotazioni.formerjob.Service;


import com.gestionaleprenotazioni.formerjob.Model.*;
import com.gestionaleprenotazioni.formerjob.Model.Event;
import com.gestionaleprenotazioni.formerjob.Repository.PaymentRepository;
import com.gestionaleprenotazioni.formerjob.Repository.TicketRepository;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class PdfService {

    private final PaymentRepository paymentRepository;
    private final TicketRepository ticketRepository;
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    public PdfService(PaymentRepository paymentRepository, TicketRepository ticketRepository) {
        this.paymentRepository = paymentRepository;
        this.ticketRepository = ticketRepository;
    }

    // ===== LEGACY METHOD =====
    public byte[] generaPdfOrdine(Long ordineId, String cliente, double totale) {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Document document = new Document();
            PdfWriter.getInstance(document, out);

            document.open();
            document.add(new Paragraph("Riepilogo ordine #" + ordineId));
            document.add(new Paragraph("Cliente: " + cliente));
            document.add(new Paragraph(" "));

            PdfPTable table = new PdfPTable(2);
            table.addCell("Campo");
            table.addCell("Valore");
            table.addCell("Ordine");
            table.addCell(String.valueOf(ordineId));
            table.addCell("Cliente");
            table.addCell(cliente);
            table.addCell("Totale");
            table.addCell(String.valueOf(totale));

            document.add(table);
            document.close();

            return out.toByteArray();
        } catch (Exception e) {
            throw new IllegalStateException("Errore generazione PDF", e);
        }
    }

    // ===== PAYMENT PDF =====
    public byte[] generaPdfPagamento(Integer paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Pagamento non trovato con ID: " + paymentId));

        User user = payment.getUser();

        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Document document = new Document();
            PdfWriter.getInstance(document, out);

            document.open();

            // Titolo
            Paragraph title = new Paragraph("RICEVUTA DI PAGAMENTO");
            title.setAlignment(Element.ALIGN_CENTER);
            Font boldFont = new Font(Font.HELVETICA, 14, Font.BOLD);
            title.setFont(boldFont);
            document.add(title);
            document.add(new Paragraph(" "));

            // Info pagamento
            document.add(new Paragraph("ID Pagamento: " + payment.getId()));
            document.add(new Paragraph("Data: " + dateFormat.format(payment.getDate())));
            document.add(new Paragraph("Metodo: " + payment.getMethod()));
            document.add(new Paragraph(" "));

            // Info cliente
            document.add(new Paragraph("CLIENTE:"));
            if (user != null) {
                document.add(new Paragraph("Nome: " + (user.getName() != null ? user.getName() : "N/A")));
                document.add(new Paragraph("Cognome: " + (user.getSurname() != null ? user.getSurname() : "N/A")));
                document.add(new Paragraph("Email: " + (user.getEmail() != null ? user.getEmail() : "N/A")));
            }
            document.add(new Paragraph(" "));

            // Tabella dettagli
            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(100);

            table.addCell(createHeaderCell("Descrizione"));
            table.addCell(createHeaderCell("Importo"));

            table.addCell("Importo Totale");
            table.addCell(String.format("%.2f €", payment.getTotalPrice()));

            table.addCell("Metodo Pagamento");
            table.addCell(payment.getMethod().toString());

            table.addCell("Data Pagamento");
            table.addCell(dateFormat.format(payment.getDate()));

            document.add(table);
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Grazie per il pagamento!"));

            document.close();

            return out.toByteArray();
        } catch (Exception e) {
            throw new IllegalStateException("Errore generazione PDF pagamento", e);
        }
    }

    // ===== TICKET PDF =====
    public byte[] generaPdfBiglietto(Integer ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Biglietto non trovato con ID: " + ticketId));

        Event event = ticket.getEvent();

        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Document document = new Document();
            PdfWriter.getInstance(document, out);

            document.open();

            // Titolo
            Paragraph title = new Paragraph("BIGLIETTO EVENTO");
            title.setAlignment(Element.ALIGN_CENTER);
            Font boldFont = new Font(Font.HELVETICA, 16, Font.BOLD);
            title.setFont(boldFont);
            document.add(title);
            document.add(new Paragraph(" "));

            // Tabella principale
            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(100);

            // Intestazione
            table.addCell(createHeaderCell("Campo"));
            table.addCell(createHeaderCell("Valore"));

            // Dati biglietto
            table.addCell("ID Biglietto");
            table.addCell(String.valueOf(ticket.getId()));

            table.addCell("Nome");
            table.addCell(ticket.getName() != null ? ticket.getName() : "N/A");

            table.addCell("Cognome");
            table.addCell(ticket.getSurname() != null ? ticket.getSurname() : "N/A");

            table.addCell("Prezzo");
            table.addCell(String.format("%.2f €", ticket.getPrice()));

            table.addCell("Data Creazione");
            table.addCell(ticket.getCreationDate() != null ? dateFormat.format(java.sql.Timestamp.valueOf(ticket.getCreationDate())) : "N/A");

            // Dati evento (se disponibile)
            if (event != null) {
                document.add(table);
                document.add(new Paragraph(" "));
                document.add(new Paragraph("EVENTO:"));
                document.add(new Paragraph(" "));

                PdfPTable eventTable = new PdfPTable(2);
                eventTable.setWidthPercentage(100);

                eventTable.addCell(createHeaderCell("Campo"));
                eventTable.addCell(createHeaderCell("Valore"));

                eventTable.addCell("Nome Evento");
                eventTable.addCell(event.getName() != null ? event.getName() : "N/A");

                eventTable.addCell("Descrizione");
                eventTable.addCell(event.getDescription() != null ? event.getDescription() : "N/A");

                eventTable.addCell("Luogo");
                eventTable.addCell(event.getLocation() != null ? event.getLocation() : "N/A");

                eventTable.addCell("Data Evento");
                eventTable.addCell(event.getDate() != null ? dateFormat.format(event.getDate()) : "N/A");

                eventTable.addCell("Tipo");
                eventTable.addCell(event.getType() != null ? event.getType().toString() : "N/A");

                document.add(eventTable);
            } else {
                document.add(table);
            }

            document.add(new Paragraph(" "));
            document.add(new Paragraph("Conserva questo biglietto - Presentalo al controllo ingresso"));

            document.close();

            return out.toByteArray();
        } catch (Exception e) {
            throw new IllegalStateException("Errore generazione PDF biglietto", e);
        }
    }

    // ===== EVENT REPORT PDF =====
    public byte[] generaPdfReportoEvento(Event event, List<Ticket> tickets) {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Document document = new Document();
            PdfWriter.getInstance(document, out);

            document.open();

            // Titolo
            Paragraph title = new Paragraph("RAPPORTO EVENTO");
            title.setAlignment(Element.ALIGN_CENTER);
            Font boldFont = new Font(Font.HELVETICA, 14, Font.BOLD);
            title.setFont(boldFont);
            document.add(title);
            document.add(new Paragraph(" "));

            // Info evento
            document.add(new Paragraph("INFORMAZIONI EVENTO:"));
            if (event != null) {
                document.add(new Paragraph("Nome: " + (event.getName() != null ? event.getName() : "N/A")));
                document.add(new Paragraph("Descrizione: " + (event.getDescription() != null ? event.getDescription() : "N/A")));
                document.add(new Paragraph("Luogo: " + (event.getLocation() != null ? event.getLocation() : "N/A")));
                document.add(new Paragraph("Data: " + (event.getDate() != null ? dateFormat.format(event.getDate()) : "N/A")));
                document.add(new Paragraph("Tipo: " + (event.getType() != null ? event.getType() : "N/A")));
                document.add(new Paragraph("Prezzo Biglietto: " + String.format("%.2f €", event.getTicketPrice())));
            }
            document.add(new Paragraph(" "));

            // Statistiche
            document.add(new Paragraph("STATISTICHE VENDITA:"));
            int totalTickets = tickets != null ? tickets.size() : 0;
            double totalRevenue = tickets != null ? tickets.stream()
                    .mapToDouble(Ticket::getPrice)
                    .sum() : 0;

            document.add(new Paragraph("Biglietti Venduti: " + totalTickets));
            document.add(new Paragraph("Biglietti Disponibili: " + (event != null ? event.getMaxTickets() : "N/A")));
            document.add(new Paragraph("Ricavo Totale: " + String.format("%.2f €", totalRevenue)));
            document.add(new Paragraph(" "));

            // Tabella biglietti
            if (tickets != null && !tickets.isEmpty()) {
                document.add(new Paragraph("DETTAGLIO BIGLIETTI:"));
                PdfPTable table = new PdfPTable(5);
                table.setWidthPercentage(100);

                table.addCell(createHeaderCell("ID"));
                table.addCell(createHeaderCell("Nome"));
                table.addCell(createHeaderCell("Cognome"));
                table.addCell(createHeaderCell("Prezzo"));
                table.addCell(createHeaderCell("Data"));

                for (Ticket ticket : tickets) {
                    table.addCell(String.valueOf(ticket.getId()));
                    table.addCell(ticket.getName() != null ? ticket.getName() : "N/A");
                    table.addCell(ticket.getSurname() != null ? ticket.getSurname() : "N/A");
                    table.addCell(String.format("%.2f €", ticket.getPrice()));
                    table.addCell(ticket.getCreationDate() != null ? dateFormat.format(java.sql.Timestamp.valueOf(ticket.getCreationDate())) : "N/A");
                }

                document.add(table);
            }

            document.close();

            return out.toByteArray();
        } catch (Exception e) {
            throw new IllegalStateException("Errore generazione PDF rapporto evento", e);
        }
    }

    // ===== UTILITY =====
    private PdfPCell createHeaderCell(String content) {
        PdfPCell cell = new PdfPCell(new Phrase(content));
        cell.setBackgroundColor(new Color(200, 200, 200));
        cell.setPadding(5);
        return cell;
    }
}
