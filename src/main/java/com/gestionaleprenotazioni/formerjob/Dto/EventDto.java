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
    private Type type; // tipologia di evento
    private PlaceDto place; // Posto a sedere dell'evento
    private List<TicketDto> tickets; // Biglietti collegati all'evento
}
