package com.gestionaleprenotazioni.formerjob.Dto;

import com.gestionaleprenotazioni.formerjob.Model.PaymentMethod;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Modello per il trasferimento dei dati di un pagamento")
public class PaymentDto {

    /**
     * Identificatore univoco del pagamento.
     * Corrisponde alla chiave primaria nel database.
     */
    @Schema(description = "ID univoco del pagamento generato dal sistema", example = "1")
    private Integer id;

    /**
     * Metodo di pagamento utilizzato per la transazione.
     */
    @Schema(description = "Metodo utilizzato (es. PAYPAL, CREDIT_CARD)", example = "PAYPAL")
    private PaymentMethod method;

    /**
     * Importo totale della transazione espresso in formato double (es. 99.99).
     */
    @Schema(description = "Costo totale della transazione", example = "99.99")
    private double totalPrice;

    /**
     * Data e ora in cui è avvenuto il pagamento.
     * <p>
     * Utilizza il formato ISO-8601: {@code yyyy-MM-dd'T'HH:mm:ss}.
     * Esempio: 2026-04-03T10:47:30
     * </p>
     */
    @Schema(description = "Data e ora della transazione (ISO-8601)", example = "2026-04-03T14:30:00")
    private LocalDateTime date;

    /**
     * Identificatore univoco dell'utente (ID) associato al pagamento.
     * <p>
     * Viene utilizzato l'ID per minimizzare il payload della richiesta
     * e mantenere il disaccoppiamento tra le entità.
     * </p>
     */
    @Schema(description = "ID dell'utente che ha effettuato il pagamento", example = "101")
    private Integer userId;
}