package com.gestionaleprenotazioni.formerjob.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile; //import per @Profile
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
@Profile("prod") //questa classe è attiva solo quando il profilo è "prod"
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable()) // disabilitiamo CSRF per le API REST
                .authorizeHttpRequests(auth -> auth

                        // registrazione pubblica
                        .requestMatchers(HttpMethod.POST, "/auth/register").permitAll()

                        // solo ADMIN può inserire, aggiornare e cancellare
                        .requestMatchers(HttpMethod.POST, "/api/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/**").hasAuthority("ROLE_ADMIN")

                        // tutti gli utenti autenticati possono leggere
                        .requestMatchers(HttpMethod.GET, "/api/**").authenticated()

                        // tutto il resto richiede autenticazione
                        .anyRequest().authenticated()
                )
                .httpBasic(basic -> {}); // usiamo Basic Auth

        return http.build();
    }
}