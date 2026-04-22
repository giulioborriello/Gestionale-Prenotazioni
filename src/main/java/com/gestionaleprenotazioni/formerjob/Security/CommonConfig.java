package com.gestionaleprenotazioni.formerjob.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Configurazione globale: bean disponibili su TUTTI i profili (dev, prod, etc.)
 */
@Configuration
public class CommonConfig {


    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}

