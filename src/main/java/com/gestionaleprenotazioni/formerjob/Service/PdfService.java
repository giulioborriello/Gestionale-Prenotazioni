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
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

@Service
public class PdfService {

    private static final String PATHS = "paths";
    private static final String PARAMETERS = "parameters";
    private static final String RESPONSES = "responses";
    private static final String SCHEMA = "schema";
    private static final String TYPE = "type";
    private static final String DESCRIPTION = "description";

    public byte[] generaPdfOrdine(
            String username,
            String surname,
            Double price,
            String location,
            String eventName,
            Date eventDate
    ) {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            Document document = new Document(PageSize.A4, 40, 40, 50, 50);
            PdfWriter.getInstance(document, out);
            document.open();

            // ===============================
            // FONT
            // ===============================

            Font titleFont = new Font(Font.HELVETICA, 24, Font.BOLD, new Color(25, 55, 109));
            Font sectionFont = new Font(Font.HELVETICA, 14, Font.BOLD, new Color(33, 33, 33));
            Font labelFont = new Font(Font.HELVETICA, 11, Font.BOLD, Color.DARK_GRAY);
            Font valueFont = new Font(Font.HELVETICA, 11, Font.NORMAL, Color.BLACK);
            Font footerFont = new Font(Font.HELVETICA, 10, Font.ITALIC, Color.GRAY);
            Font priceFont = new Font(Font.HELVETICA, 16, Font.BOLD, new Color(0, 128, 0));

            // ===============================
            // HEADER
            // ===============================

            Paragraph title = new Paragraph("BIGLIETTO EVENTO", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(10);
            document.add(title);

            Paragraph subtitle = new Paragraph(
                    "Conferma ufficiale della tua prenotazione",
                    new Font(Font.HELVETICA, 11, Font.ITALIC, Color.GRAY)
            );
            subtitle.setAlignment(Element.ALIGN_CENTER);
            subtitle.setSpacingAfter(25);
            document.add(subtitle);

            // ===============================
            // EVENT INFO
            // ===============================

            Paragraph eventHeader = new Paragraph("INFORMAZIONI EVENTO", sectionFont);
            eventHeader.setSpacingAfter(10);
            document.add(eventHeader);

            PdfPTable eventTable = new PdfPTable(2);
            eventTable.setWidthPercentage(100);
            eventTable.setSpacingAfter(20);

            addStyledRow(eventTable, "Evento", eventName, labelFont, valueFont);
            addStyledRow(eventTable, "Data", formatDate(eventDate), labelFont, valueFont);
            addStyledRow(eventTable, "Location", location, labelFont, valueFont);

            document.add(eventTable);

            // ===============================
            // USER INFO
            // ===============================

            Paragraph userHeader = new Paragraph("DETTAGLI UTENTE", sectionFont);
            userHeader.setSpacingAfter(10);
            document.add(userHeader);

            PdfPTable userTable = new PdfPTable(2);
            userTable.setWidthPercentage(100);
            userTable.setSpacingAfter(20);

            addStyledRow(userTable, "Nome", username, labelFont, valueFont);
            addStyledRow(userTable, "Cognome", surname, labelFont, valueFont);

            document.add(userTable);

            // ===============================
            // PRICE BOX
            // ===============================

            PdfPTable priceTable = new PdfPTable(1);
            priceTable.setWidthPercentage(100);
            priceTable.setSpacingBefore(10);
            priceTable.setSpacingAfter(25);

            PdfPCell priceCell = new PdfPCell();
            priceCell.setBackgroundColor(new Color(230, 255, 230));
            priceCell.setPadding(15);
            priceCell.setBorderColor(new Color(0, 150, 0));

            Paragraph priceParagraph = new Paragraph(
                    "IMPORTO PAGATO: € " + String.format("%.2f", price),
                    priceFont
            );
            priceParagraph.setAlignment(Element.ALIGN_CENTER);

            priceCell.addElement(priceParagraph);
            priceTable.addCell(priceCell);

            document.add(priceTable);

            // ===============================
            // FOOTER
            // ===============================

            Paragraph footer = new Paragraph(
                    "Grazie per aver scelto EventIO\nTi auguriamo una splendida esperienza!",
                    footerFont
            );
            footer.setAlignment(Element.ALIGN_CENTER);
            footer.setSpacingBefore(20);

            document.add(footer);

            document.close();

            return out.toByteArray();

        } catch (Exception e) {
            throw new PdfGenerationException("Errore generazione PDF ordine", e);
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
            JsonNode paths = root.get(PATHS);

            if (paths != null) {
                Iterator<String> pathIterator = paths.fieldNames();
                while (pathIterator.hasNext()) {
                    String path = pathIterator.next();
                    JsonNode methods = paths.get(path);
                    if (methods == null) {
                        continue;
                    }

                    Iterator<String> methodIterator = methods.fieldNames();
                    while (methodIterator.hasNext()) {
                        String method = methodIterator.next();
                        JsonNode operation = methods.get(method);
                        addEndpointSection(document, path, method, operation);
                    }
                }
            }

            document.close();

            byte[] pdfBytes = out.toByteArray();

            // 📁 SALVATAGGIO FILE
            Path filePath = Paths.get("src/main/resources/static/docs/documentazione.pdf");
            Files.createDirectories(filePath.getParent());
            Files.write(filePath, pdfBytes);

            return pdfBytes;

        } catch (DocumentException | IOException e) {
            throw new PdfGenerationException("Errore generazione PDF documentazione", e);
        }
    }

    private void addStyledRow(
            PdfPTable table,
            String label,
            String value,
            Font labelFont,
            Font valueFont
    ) {
        PdfPCell labelCell = new PdfPCell(new Phrase(label, labelFont));
        labelCell.setBackgroundColor(new Color(245, 245, 245));
        labelCell.setPadding(10);
        labelCell.setBorderColor(new Color(220, 220, 220));

        PdfPCell valueCell = new PdfPCell(new Phrase(value != null ? value : "-", valueFont));
        valueCell.setPadding(10);
        valueCell.setBorderColor(new Color(220, 220, 220));

        table.addCell(labelCell);
        table.addCell(valueCell);
    }

    private void addEndpointSection(Document document, String path, String method, JsonNode operation) throws DocumentException {
        Font summaryFont = addEndpointHeader(document, path, method, operation);
        addParametersSection(document, operation, summaryFont);
        addResponsesSection(document, operation, summaryFont);
        document.add(new Paragraph("---------------------------------------------------------"));
    }

    private Font addEndpointHeader(Document document, String path, String method, JsonNode operation) throws DocumentException {
        Font endpointFont = new Font(Font.HELVETICA, 14, Font.BOLD, Color.BLACK);
        Paragraph endpointTitle = new Paragraph(method.toUpperCase() + " " + path, endpointFont);
        endpointTitle.setSpacingBefore(10);
        endpointTitle.setSpacingAfter(5);
        document.add(endpointTitle);

        String summary = operation.has("summary") ? operation.get("summary").asText() : "-";
        Font summaryFont = new Font(Font.HELVETICA, 11, Font.ITALIC, Color.DARK_GRAY);
        document.add(new Paragraph("Descrizione: " + summary, summaryFont));
        return summaryFont;
    }

    private void addParametersSection(Document document, JsonNode operation, Font summaryFont) throws DocumentException {
        JsonNode parameters = operation.get(PARAMETERS);
        if (parameters != null && !parameters.isEmpty()) {
            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100);
            table.setSpacingBefore(5);
            table.setSpacingAfter(10);

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

            for (JsonNode param : parameters) {
                table.addCell(param.has("name") ? param.get("name").asText() : "-");
                table.addCell(param.has("in") ? param.get("in").asText() : "-");
                table.addCell(extractParameterType(param));
                table.addCell(param.has("required") ? String.valueOf(param.get("required").asBoolean()) : "-");
            }

            document.add(table);
            return;
        }

        document.add(new Paragraph("Parametri: nessuno", summaryFont));
    }

    private String extractParameterType(JsonNode param) {
        JsonNode schemaNode = param.get(SCHEMA);
        if (schemaNode != null && schemaNode.has(TYPE)) {
            return schemaNode.get(TYPE).asText();
        }
        return "-";
    }

    private void addResponsesSection(Document document, JsonNode operation, Font summaryFont) throws DocumentException {
        JsonNode responses = operation.get(RESPONSES);
        if (responses != null) {
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

            Iterator<String> responseCodes = responses.fieldNames();
            while (responseCodes.hasNext()) {
                String code = responseCodes.next();
                JsonNode resp = responses.get(code);
                respTable.addCell(code);
                respTable.addCell(resp.has(DESCRIPTION) ? resp.get(DESCRIPTION).asText() : "-");
            }

            document.add(respTable);
            return;
        }

        document.add(new Paragraph("Risposte: nessuna", summaryFont));
    }


    private static class PdfGenerationException extends RuntimeException {
        private PdfGenerationException(String message, Throwable cause) {
            super(message, cause);
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
