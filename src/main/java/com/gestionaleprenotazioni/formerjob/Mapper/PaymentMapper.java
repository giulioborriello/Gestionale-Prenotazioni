package com.gestionaleprenotazioni.formerjob.Mapper;

import com.gestionaleprenotazioni.formerjob.Dto.PaymentDto;
import com.gestionaleprenotazioni.formerjob.Model.Payment;
import com.gestionaleprenotazioni.formerjob.Model.PaymentMethod;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PaymentMapper implements Converter<Payment, PaymentDto> {

    @Override
    public PaymentDto toDTO(Payment entity) {
        if (entity == null) return null;

        PaymentDto dto = new PaymentDto();
        dto.setId(entity.getId());
        dto.setAmount(entity.getCart() != null ? entity.getCart().getTotalPrice() : 0.0);
        dto.setStatus(entity.getChecked() ? "COMPLETED" : "PENDING");
        dto.setMethod(entity.getMethod().name()); // Trasforma Enum in String
        dto.setAttemptedAt(entity.getDate());

        if (entity.getCart() != null) {
            dto.setOrderId(entity.getCart().getId());
        }

        return dto;
    }

    @Override
    public Payment toEntity(PaymentDto dto) {
        if (dto == null) return null;

        Payment entity = new Payment();
        entity.setId(dto.getId());
        entity.setChecked(dto.getStatus().equalsIgnoreCase("COMPLETED"));
        entity.setDate(dto.getAttemptedAt());

        // Trasforma String in Enum
        if (dto.getMethod() != null) {
            entity.setMethod(PaymentMethod.valueOf(dto.getMethod().toUpperCase()));
        }

        return entity;
    }

    @Override
    public List<PaymentDto> toDTOList(List<Payment> entities) {
        return entities.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<Payment> toEntityList(List<PaymentDto> dtos) {
        return dtos.stream().map(this::toEntity).collect(Collectors.toList());
    }
}