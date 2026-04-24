package com.gestionaleprenotazioni.formerjob.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Entita JPA che rappresenta un ticket nel dominio applicativo.
 *
 * <p>La classe e mappata alla tabella `formerjob.ticket` e contiene
 * le informazioni principali del biglietto, inclusi i riferimenti
 * all'utente proprietario e all'evento associato.</p>
 *
 * <p>Lombok genera automaticamente getter, setter, costruttore vuoto
 * e costruttore completo.</p>
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ticket", schema = "formerjob")
public class Ticket {

    /**
     * Identificativo univoco del ticket.
     *
     * <p>Valore generato automaticamente dal database con strategia IDENTITY.</p>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Nome dell'intestatario del ticket.
     *
     * <p>Campo obbligatorio.</p>
     */
    @Column(nullable = false)
    private String name;

    /**
     * Cognome dell'intestatario del ticket.
     *
     * <p>Campo obbligatorio.</p>
     */
    @Column(nullable = false)
    private String surname;

    /**
     * Prezzo del ticket.
     *
     * <p>Campo obbligatorio.</p>
     */
    @Column(nullable = false)
    private Double price;

    /**
     * Data e ora di creazione del ticket.
     *
     * <p>Campo obbligatorio.</p>
     */
    @Column(nullable = false)
    private LocalDateTime creationDate;

    /**
     * Utente associato al ticket.
     *
     * <p>Relazione molti-a-uno caricata in modo lazy.
     * Mappata tramite la colonna `user_id` (obbligatoria).</p>
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * Evento associato al ticket.
     *
     * <p>Relazione molti-a-uno caricata in modo lazy.
     * Mappata tramite la colonna `event_id` (obbligatoria).</p>
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    /**
     * Pagamento associato al ticket.
     *
     * <p>Relazione molti-a-uno caricata in modo lazy.
     * Mappata tramite la colonna `payment_id` (obbligatoria).</p>
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id", nullable = false)
    private Payment payment;
}
