package com.gestionaleprenotazioni.formerjob.Dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.List;

// Classe DTO (Data Transfer Object) per il carrello
// Usata per trasportare i dati tra client e server senza esporre direttamente la Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartDto {

    // Identificativo univoco del carrello
    private Integer id;

    // Prezzo totale dei biglietti nel carrello
    private Double totalPrice;

    // ID dell'utente proprietario del carrello
    // Invece dell'oggetto User intero, passiamo solo l'id per mantenere il DTO leggero
    private Integer userId;

    // ID del pagamento associato al carrello (può essere null se non ancora pagato)
    private List<Integer> paymentIds;

    // Lista degli ID dei biglietti presenti nel carrello
    // Invece della lista di oggetti Ticket, passiamo solo i loro id
    private List<Integer> ticketIds;
}