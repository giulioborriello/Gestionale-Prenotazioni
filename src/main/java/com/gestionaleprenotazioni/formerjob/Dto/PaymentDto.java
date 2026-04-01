package com.gestionaleprenotazioni.formerjob.Dto;

import com.gestionaleprenotazioni.formerjob.Model.PaymentMethod;
import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDto {


    private Integer id;

    private PaymentMethod method;

    private double totalPrice;

    private LocalDateTime date;


}