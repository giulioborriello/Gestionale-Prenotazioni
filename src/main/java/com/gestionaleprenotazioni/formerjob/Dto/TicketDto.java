package com.gestionaleprenotazioni.formerjob.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TicketDto {
    private Integer ticketId;
    private String name;
    private String surname;
    private Double price;
    private LocalDateTime creationDate;
}
