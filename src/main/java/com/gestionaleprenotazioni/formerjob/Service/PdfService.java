package com.gestionaleprenotazioni.formerjob.Service;


import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class PdfService {

    public byte[] generaPdfOrdine(String username, String surname, Double price, String location, String eventName, Date eventDate) {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Document document = new Document();
            document.setMargins(40, 40, 40, 40);
            PdfWriter.getInstance(document, out);

            document.open();

            // Titolo principale
            Paragraph title = new Paragraph("Riepilogo Prenotazione", new Font(Font.HELVETICA, 24, Font.BOLD));
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(30);
            document.add(title);

            // Linea decorativa
            Paragraph line = new Paragraph("_____________________________________________________");
            line.setAlignment(Element.ALIGN_CENTER);
            line.setSpacingAfter(20);
            document.add(line);

            // Sezione Info Evento
            Paragraph eventSection = new Paragraph("Informazioni Evento", new Font(Font.HELVETICA, 14, Font.BOLD));
            eventSection.setSpacingBefore(10);
            eventSection.setSpacingAfter(10);
            document.add(eventSection);

            PdfPTable eventTable = new PdfPTable(2);
            eventTable.setWidthPercentage(100);
            
            addRowToTable(eventTable, "Nome Evento:", eventName);
            addRowToTable(eventTable, "Data e Ora:", formatDate(eventDate));
            addRowToTable(eventTable, "Luogo:", location);

            document.add(eventTable);
            document.add(new Paragraph(" "));

            // Sezione Info Passeggero
            Paragraph passengerSection = new Paragraph("Dettagli Utente", new Font(Font.HELVETICA, 14, Font.BOLD));
            passengerSection.setSpacingBefore(10);
            passengerSection.setSpacingAfter(10);
            document.add(passengerSection);

            PdfPTable passengerTable = new PdfPTable(2);
            passengerTable.setWidthPercentage(100);

            addRowToTable(passengerTable, "Nome:", username);
            addRowToTable(passengerTable, "Cognome:", surname);

            document.add(passengerTable);
            document.add(new Paragraph(" "));

            // Sezione Prezzo
            Paragraph priceSection = new Paragraph("Dettagli Prezzo", new Font(Font.HELVETICA, 14, Font.BOLD));
            priceSection.setSpacingBefore(10);
            priceSection.setSpacingAfter(10);
            document.add(priceSection);

            PdfPTable priceTable = new PdfPTable(2);
            priceTable.setWidthPercentage(100);

            PdfPCell priceCell = new PdfPCell();
            priceCell.setBackgroundColor(new Color(100, 200, 100));
            priceCell.setPadding(8);
            priceCell.setPhrase(new Phrase("Prezzo:", new Font(Font.HELVETICA, 12, Font.BOLD)));

            PdfPCell priceCellValue = new PdfPCell();
            priceCellValue.setBackgroundColor(new Color(200, 255, 200));
            priceCellValue.setPadding(8);
            priceCellValue.setPhrase(new Phrase("€ " + String.format("%.2f", price), new Font(Font.HELVETICA, 12, Font.BOLD)));

            priceTable.addCell(priceCell);
            priceTable.addCell(priceCellValue);

            document.add(priceTable);
            document.add(new Paragraph(" "));

            // Footer
            Paragraph footer = new Paragraph("Grazie per la tua prenotazione!", new Font(Font.HELVETICA, 10, Font.ITALIC));
            footer.setAlignment(Element.ALIGN_CENTER);
            footer.setSpacingBefore(20);
            document.add(footer);

            document.close();

            return out.toByteArray();
        } catch (Exception e) {
            throw new IllegalStateException("Errore generazione PDF", e);
        }
    }

    private void addRowToTable(PdfPTable table, String label, String value) {
        PdfPCell labelCell = new PdfPCell();
        labelCell.setBackgroundColor(new Color(230, 230, 230));
        labelCell.setPadding(8);
        labelCell.setPhrase(new Phrase(label, new Font(Font.HELVETICA, 11, Font.BOLD)));
        table.addCell(labelCell);

        PdfPCell valueCell = new PdfPCell();
        valueCell.setPadding(8);
        valueCell.setPhrase(new Phrase(value != null ? value : "-", new Font(Font.HELVETICA, 11)));
        table.addCell(valueCell);
    }

    private String formatDate(Date date) {
        if (date == null) return "-";
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        return sdf.format(date);
    }
}
