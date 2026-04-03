package com.gestionaleprenotazioni.formerjob.Dto;

import com.gestionaleprenotazioni.formerjob.Model.PaymentMethod;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Data Transfer Object (DTO) per la gestione dei dati di pagamento.
 * <p>
 * Questa classe facilita il trasferimento dei dati relativi a una transazione
 * tra il client e il server, ottimizzando le performance includendo solo
 * l'ID dell'utente invece dell'intero oggetto di dominio.
 * </p>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDto {

    /**
     * Identificatore univoco del pagamento.
     * Corrisponde alla chiave primaria nel database.
     */
    private Integer id;
    
    /**
     * Metodo di pagamento utilizzato per la transazione.
     */
    private PaymentMethod method;
    
    /**
     * Importo totale della transazione espresso in formato double (es. 99.99).
     */
    private double totalPrice;

    /**
     * Data e ora in cui è avvenuto il pagamento.
     * <p>
     * Utilizza il formato ISO-8601: {@code yyyy-MM-dd'T'HH:mm:ss}.
     * Esempio: 2026-04-03T10:47:30
     * </p>
     */
    private LocalDateTime date;

    /**
     * Identificatore univoco dell'utente (ID) associato al pagamento.
     * <p>
     * Viene utilizzato l'ID per minimizzare il payload della richiesta
     * e mantenere il disaccoppiamento tra le entità.
     * </p>
     */
    private Integer userId;
}