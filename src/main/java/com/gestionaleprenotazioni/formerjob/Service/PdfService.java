package com.gestionaleprenotazioni.formerjob.Service;


import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

@Service
public class PdfService {

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
}
