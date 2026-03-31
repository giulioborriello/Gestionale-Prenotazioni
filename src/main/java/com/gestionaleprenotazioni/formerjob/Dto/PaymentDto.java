package com.gestionaleprenotazioni.formerjob.Dto;

import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDto {


    private Integer id;

    private Integer orderId;

    private Double amount;

    private String status;

    private String method;

    private String transactionId;

    private LocalDateTime attemptedAt;
}