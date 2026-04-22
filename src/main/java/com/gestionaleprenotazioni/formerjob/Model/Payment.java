package com.gestionaleprenotazioni.formerjob.Model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import com.gestionaleprenotazioni.formerjob.Model.Event;

/**
 * Entità JPA che rappresenta la tabella {@code payment} nel database.
 * <p>
 * Questa classe definisce la struttura dei dati di pagamento memorizzati
 * nello schema {@code formerjob}. Include dettagli sull'importo, il metodo
 * utilizzato e il riferimento all'utente che ha effettuato la transazione.
 * </p>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "payment", schema = "formerjob")
public class Payment {

    /**
     * Identificatore univoco del pagamento.
     * Generato automaticamente dal database tramite strategia {@code IDENTITY}.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Metodo di pagamento utilizzato.
     * Memorizzato nel database come stringa tramite {@link EnumType#STRING}.
     * Questo campo è obbligatorio (NOT NULL).
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentMethod method;

    /**
     * Importo totale della transazione effettuata.
     */
    private double totalPrice;

    /**
     * Data e ora esatta della registrazione del pagamento.
     * Questo campo è obbligatorio (NOT NULL).
     */
    @Column(nullable = false)
    private LocalDateTime date;

    /**
     * Riferimento all'utente che ha effettuato il pagamento.
     * <p>
     * Utilizza una relazione {@code ManyToOne} con caricamento di tipo {@code LAZY}
     * per ottimizzare le performance, evitando il caricamento dei dati utente
     * se non esplicitamente richiesto.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * Riferimento all'evento acquistato con questo pagamento.
     * Utilizza una relazione ManyToOne (molti pagamenti per un evento).
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;


}