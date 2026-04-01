package com.gestionaleprenotazioni.formerjob.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlaceDto {
    private Integer id;
    private String nome;
    private String code;
    private boolean status;
    private String type;
    private Integer eventId; // id dell'evento a cui appartiene il posto
}