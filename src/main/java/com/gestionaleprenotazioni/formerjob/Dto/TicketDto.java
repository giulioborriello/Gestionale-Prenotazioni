package com.gestionaleprenotazioni.formerjob.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Data Transfer Object (DTO) che rappresenta un ticket nel layer API/service.
 *
 * <p>Questa classe viene usata per trasferire i dati del ticket tra i vari layer
 * dell'applicazione senza esporre direttamente l'entita' persistente.</p>
 *
 * <p>Lombok genera automaticamente:
 * <ul>
 *   <li>Getter e setter per tutti i campi ({@link Getter}, {@link Setter})</li>
 *   <li>Costruttore vuoto ({@link NoArgsConstructor})</li>
 *   <li>Costruttore completo con tutti i campi ({@link AllArgsConstructor})</li>
 * </ul>
 * </p>
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TicketDto {

    /**
     * Identificativo univoco del ticket.
     */
    private Integer ticketId;

    /**
     * Nome dell'intestatario del ticket.
     */
    private String name;

    /**
     * Cognome dell'intestatario del ticket.
     */
    private String surname;

    /**
     * Prezzo del ticket.
     */
    private Double price;

    /**
     * Data e ora di creazione del ticket.
     */
    private LocalDateTime creationDate;

    /**
     * Identificativo dell'utente associato al ticket.
     */
    private Integer userId;

    /**
     * Identificativo dell'evento associato al ticket.
     */
    private Integer eventId;

    private Integer paymentId;
}
