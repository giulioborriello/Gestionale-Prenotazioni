package com.gestionaleprenotazioni.formerjob.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseOrderDto {
    // I dati relativi alla transazione finanziaria
    private PaymentDto payment;

    // La lista dei biglietti da generare (con nomi e cognomi)
    private List<TicketDto> tickets;
}