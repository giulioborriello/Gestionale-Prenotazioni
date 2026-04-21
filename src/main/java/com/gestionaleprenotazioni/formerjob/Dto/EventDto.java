package com.gestionaleprenotazioni.formerjob.Dto;

import com.gestionaleprenotazioni.formerjob.Model.Type;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EventDto {

    private Integer id; // identificativo dell'evento
    private String name; // nome dell'evento
    private String description; // descrizione dell'evento
    private String location; // luogo dell'evento
    private Date date; // data dell'evento
    private Integer maxTickets; // numero massimo di biglietti per evento
    private Integer selledTickets; // numero di biglietti venduti per un evento
    private Double ticketPrice; // Prezzo del biglietto per l'evento
    private Type type; // tipologia di evento
    private List<TicketDto> tickets; // Biglietti collegati all'evento
}
