package com.gestionaleprenotazioni.formerjob.Service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SwaggerService {

    public String getApiDocsJson() {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject("http://localhost:8081/v3/api-docs", String.class);
    }
}