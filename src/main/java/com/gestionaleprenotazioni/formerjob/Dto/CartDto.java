package com.gestionaleprenotazioni.formerjob.Dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartDto {


    private Integer id;

    private Double totalPrice;

    private Integer userId;

    private Integer paymentId;

    private List<Integer> ticketIds;
}