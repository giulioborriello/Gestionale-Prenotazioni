package com.gestionaleprenotazioni.formerjob.Service;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

    public byte[] generaPdfDaSwagger(String json) {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            Document document = new Document(PageSize.A4, 36, 36, 54, 36); // margini
            PdfWriter.getInstance(document, out);

            document.open();

            // Titolo principale
            Font titleFont = new Font(Font.HELVETICA, 20, Font.BOLD, Color.BLUE);
            Paragraph title = new Paragraph("DOCUMENTAZIONE API", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(20);
            document.add(title);

            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(json);
            JsonNode paths = root.get("paths");

            paths.fieldNames().forEachRemaining(path -> {
                try {
                    JsonNode methods = paths.get(path);
                    methods.fieldNames().forEachRemaining(method -> {
                        try {
                            JsonNode operation = methods.get(method);

                            // ✨ Titolo Endpoint
                            Font endpointFont = new Font(Font.HELVETICA, 14, Font.BOLD, Color.BLACK);
                            Paragraph endpointTitle = new Paragraph(method.toUpperCase() + " " + path, endpointFont);
                            endpointTitle.setSpacingBefore(10);
                            endpointTitle.setSpacingAfter(5);
                            document.add(endpointTitle);

                            // Descrizione
                            String summary = operation.has("summary") ? operation.get("summary").asText() : "-";
                            Font summaryFont = new Font(Font.HELVETICA, 11, Font.ITALIC, Color.DARK_GRAY);
                            document.add(new Paragraph("Descrizione: " + summary, summaryFont));

                            // Parametri
                            if (operation.has("parameters") && operation.get("parameters").size() > 0) {
                                PdfPTable table = new PdfPTable(4);
                                table.setWidthPercentage(100);
                                table.setSpacingBefore(5);
                                table.setSpacingAfter(10);

                                // Header
                                Font headerFont = new Font(Font.HELVETICA, 11, Font.BOLD, Color.WHITE);
                                PdfPCell h1 = new PdfPCell(new Phrase("Nome", headerFont));
                                PdfPCell h2 = new PdfPCell(new Phrase("In", headerFont));
                                PdfPCell h3 = new PdfPCell(new Phrase("Tipo", headerFont));
                                PdfPCell h4 = new PdfPCell(new Phrase("Required", headerFont));

                                Color headerBg = new Color(0, 121, 182);
                                h1.setBackgroundColor(headerBg);
                                h2.setBackgroundColor(headerBg);
                                h3.setBackgroundColor(headerBg);
                                h4.setBackgroundColor(headerBg);

                                table.addCell(h1);
                                table.addCell(h2);
                                table.addCell(h3);
                                table.addCell(h4);

                                for (JsonNode param : operation.get("parameters")) {
                                    table.addCell(param.has("name") ? param.get("name").asText() : "-");
                                    table.addCell(param.has("in") ? param.get("in").asText() : "-");
                                    table.addCell(param.has("schema") && param.get("schema").has("type") ?
                                            param.get("schema").get("type").asText() : "-");
                                    table.addCell(param.has("required") ? String.valueOf(param.get("required").asBoolean()) : "-");
                                }

                                document.add(table);
                            } else {
                                document.add(new Paragraph("Parametri: nessuno", summaryFont));
                            }

                            // Responses
                            if (operation.has("responses")) {
                                document.add(new Paragraph("Risposte:", summaryFont));

                                PdfPTable respTable = new PdfPTable(2);
                                respTable.setWidthPercentage(60);
                                respTable.setSpacingBefore(5);
                                respTable.setSpacingAfter(10);

                                Font respHeader = new Font(Font.HELVETICA, 11, Font.BOLD, Color.WHITE);
                                PdfPCell rh1 = new PdfPCell(new Phrase("Codice", respHeader));
                                PdfPCell rh2 = new PdfPCell(new Phrase("Descrizione", respHeader));
                                Color respHeaderBg = new Color(0, 121, 182);
                                rh1.setBackgroundColor(respHeaderBg);
                                rh2.setBackgroundColor(respHeaderBg);
                                respTable.addCell(rh1);
                                respTable.addCell(rh2);

                                operation.get("responses").fieldNames().forEachRemaining(code -> {
                                    JsonNode resp = operation.get("responses").get(code);
                                    respTable.addCell(code);
                                    respTable.addCell(resp.has("description") ? resp.get("description").asText() : "-");
                                });

                                document.add(respTable);
                            } else {
                                document.add(new Paragraph("Risposte: nessuna", summaryFont));
                            }

                            // Linea separatrice
                            document.add(new Paragraph("---------------------------------------------------------"));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            document.close();

            byte[] pdfBytes = out.toByteArray();

            // 📁 SALVATAGGIO FILE
            Path filePath = Paths.get("src/main/resources/static/docs/documentazione.pdf");
            Files.createDirectories(filePath.getParent());
            Files.write(filePath, pdfBytes);

            return pdfBytes;

        } catch (Exception e) {
            throw new RuntimeException("Errore generazione PDF", e);
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
