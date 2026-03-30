package com.gestionaleprenotazioni.formerjob.Dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

// Classe DTO (Data Transfer Object) per il pagamento
// Usata per trasportare i dati tra client e server senza esporre direttamente la Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDto {

    // Identificativo univoco del pagamento
    private Integer id;

    // ID dell'ordine a cui è associato il pagamento
    // Invece dell'oggetto Order intero, passiamo solo l'id per mantenere il DTO leggero
    private Integer orderId;

    // Importo del pagamento
    private Double amount;

    // Stato del pagamento (es. PENDING, COMPLETED, FAILED, REFUNDED)
    private String status;

    // Metodo di pagamento utilizzato (es. CREDIT_CARD, PAYPAL, STRIPE)
    private String method;

    // ID della transazione restituito dal provider di pagamento (es. Stripe, PayPal)
    private String transactionId;

    // Data e ora in cui è stato effettuato il tentativo di pagamento
    private LocalDateTime attemptedAt;
}