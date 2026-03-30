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

        // Recuperiamo il prezzo totale dal Carrello associato (che abbiamo nel progetto)
        if (entity.getCart() != null) {
            dto.setAmount(entity.getCart().getTotalPrice());
            dto.setOrderId(entity.getCart().getId());
        } else {
            dto.setAmount(0.0);
        }

        // Trasformiamo il Boolean 'checked' in una Stringa leggibile per il Frontend
        dto.setStatus(entity.getChecked() != null && entity.getChecked() ? "COMPLETED" : "PENDING");

        // Trasforma l'Enum (es. CREDIT_CARD) in una Stringa (es. "CREDIT_CARD")
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

        // Operazione inversa: Trasformiamo la stringa "COMPLETED" nel Boolean true
        if (dto.getStatus() != null) {
            entity.setChecked(dto.getStatus().equalsIgnoreCase("COMPLETED"));
        }

        entity.setDate(dto.getAttemptedAt());


        if (dto.getMethod() != null) {
            try {
                entity.setMethod(PaymentMethod.valueOf(dto.getMethod().toUpperCase()));
            } catch (IllegalArgumentException e) {
                // Se il metodo non esiste nell'Enum, possiamo gestire l'errore qui
            }
        }

        return entity;
    }


}