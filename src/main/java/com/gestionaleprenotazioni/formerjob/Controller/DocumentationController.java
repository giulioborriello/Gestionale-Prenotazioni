package com.gestionaleprenotazioni.formerjob.Controller;

import com.gestionaleprenotazioni.formerjob.Service.PdfService;
import com.gestionaleprenotazioni.formerjob.Service.SwaggerService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/docs")
public class DocumentationController {

    private final SwaggerService swaggerService;
    private final PdfService pdfService;

    public DocumentationController(SwaggerService swaggerService, PdfService pdfService) {
        this.swaggerService = swaggerService;
        this.pdfService = pdfService;
    }

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