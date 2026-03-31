package com.gestionaleprenotazioni.formerjob.Mapper;

import com.gestionaleprenotazioni.formerjob.Dto.PaymentDto;
import com.gestionaleprenotazioni.formerjob.Model.Payment;
import com.gestionaleprenotazioni.formerjob.Model.PaymentMethod;
import org.springframework.stereotype.Component;


@Component
public class PaymentMapper extends AbstractMapper<PaymentDto, Payment> {

    @Override
    public PaymentDto toDTO(Payment entity) {
        if (entity == null) return null;

        PaymentDto dto = new PaymentDto();
        dto.setId(entity.getId());


        if (entity.getCart() != null) {
            dto.setAmount(entity.getCart().getTotalPrice());
            dto.setOrderId(entity.getCart().getId());
        } else {
            dto.setAmount(0.0);
        }


        dto.setStatus(entity.getChecked() != null && entity.getChecked() ? "COMPLETED" : "PENDING");


        if (entity.getMethod() != null) {
            dto.setMethod(entity.getMethod().name());
        }

        dto.setAttemptedAt(entity.getDate());

        return dto;
    }

    @Override
    public Payment toEntity(PaymentDto dto) {
        if (dto == null) return null;

        Payment entity = new Payment();
        entity.setId(dto.getId());


        if (dto.getStatus() != null) {
            entity.setChecked(dto.getStatus().equalsIgnoreCase("COMPLETED"));
        }

        entity.setDate(dto.getAttemptedAt());


        if (dto.getMethod() != null) {
            try {
                entity.setMethod(PaymentMethod.valueOf(dto.getMethod().toUpperCase()));
            } catch (IllegalArgumentException e) {

            }
        }

        return entity;
    }


}