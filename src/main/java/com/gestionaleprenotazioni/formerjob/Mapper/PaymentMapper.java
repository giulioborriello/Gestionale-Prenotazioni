package com.gestionaleprenotazioni.formerjob.Mapper;

import com.gestionaleprenotazioni.formerjob.Dto.PaymentDto;
import com.gestionaleprenotazioni.formerjob.Model.Payment;
import com.gestionaleprenotazioni.formerjob.Model.User;
import org.springframework.stereotype.Component;


@Component
public class PaymentMapper extends AbstractMapper<PaymentDto, Payment> {

    @Override
    public PaymentDto toDTO(Payment entity) {
        if (entity == null) return null;

        PaymentDto dto = new PaymentDto();
        dto.setId(entity.getId());
        dto.setMethod(entity.getMethod());
        dto.setTotalPrice(entity.getTotalPrice());
        dto.setDate(entity.getDate());
        if (entity.getUser() != null) {
            dto.setUserId(entity.getUser().getId());
        }

        return dto;
    }

    @Override
    public Payment toEntity(PaymentDto dto) {
        if (dto == null) return null;

        Payment entity = new Payment();
        entity.setId(dto.getId());

        entity.setMethod(dto.getMethod());
        entity.setTotalPrice(dto.getTotalPrice());
        entity.setDate(dto.getDate());
        if (dto.getUserId() != null) {
            User user = new User();
            user.setId(dto.getUserId());
            entity.setUser(user);
        }

        return entity;
    }


}