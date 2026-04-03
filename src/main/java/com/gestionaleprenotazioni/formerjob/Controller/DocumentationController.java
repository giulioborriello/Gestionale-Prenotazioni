package com.gestionaleprenotazioni.formerjob.Controller;

import com.gestionaleprenotazioni.formerjob.Service.PdfService;
import com.gestionaleprenotazioni.formerjob.Service.SwaggerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/docs")
public class DocumentationController {

    @Autowired
    private SwaggerService swaggerService;

    @Autowired
    private PdfService pdfService;

    // 🔹 JSON Swagger
    @GetMapping("/json")
    public String getSwaggerJson() {
        return swaggerService.getApiDocsJson();
    }

    // 🔥 PDF Swagger
    @GetMapping("/pdf")
    public ResponseEntity<byte[]> generaPdf() {

        String json = swaggerService.getApiDocsJson();
        byte[] pdf = pdfService.generaPdfDaSwagger(json);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=documentazione.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }
}