package com.gestionaleprenotazioni.formerjob.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile; //import per @Profile
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
@Profile("prod") //questa classe è attiva solo quando il profilo è "prod"
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private static final String API_PATTERN = "/api/**";
    private static final String ROLE_ADMIN = "ROLE_ADMIN";


    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config) {
        try {
            return config.getAuthenticationManager();
        } catch (Exception e) {
            throw new IllegalStateException("Errore nella creazione di AuthenticationManager", e);
        }
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) {

        try {
            http
                    .csrf(csrf -> csrf.disable()) // disabilitiamo CSRF per le API REST
                    .authorizeHttpRequests(auth -> auth

                            // endpoint di login pubblico (nessuna autenticazione richiesta)
                            .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()

                            // solo ADMIN può inserire, aggiornare e cancellare
                            .requestMatchers(HttpMethod.POST, API_PATTERN).hasAuthority(ROLE_ADMIN)
                            .requestMatchers(HttpMethod.PUT, API_PATTERN).hasAuthority(ROLE_ADMIN)
                            .requestMatchers(HttpMethod.DELETE, API_PATTERN).hasAuthority(ROLE_ADMIN)

                            // tutti gli utenti autenticati possono leggere
                            .requestMatchers(HttpMethod.GET, API_PATTERN).authenticated()

                            // tutto il resto richiede autenticazione
                            .anyRequest().authenticated()
                    )
                    .httpBasic(basic -> {}); // usiamo Basic Auth

            return http.build();
        } catch (Exception e) {
            throw new IllegalStateException("Errore nella configurazione security prod", e);
        }
    }
}