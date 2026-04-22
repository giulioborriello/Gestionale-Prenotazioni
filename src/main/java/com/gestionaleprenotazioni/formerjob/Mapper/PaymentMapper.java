package com.gestionaleprenotazioni.formerjob.Mapper;

import com.gestionaleprenotazioni.formerjob.Dto.PaymentDto;
import com.gestionaleprenotazioni.formerjob.Model.Payment;
import com.gestionaleprenotazioni.formerjob.Model.PaymentMethod;
import com.gestionaleprenotazioni.formerjob.Model.User;
import java.time.LocalDateTime;
import org.springframework.stereotype.Component;

/**
 * Componente Mapper per la conversione tra {@link Payment} e {@link PaymentDto}.
 */
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

        // CORREZIONE: Imposta un valore di default valido per l'Enum.
        // Sostituisci 'PaymentMethod.CASH' con uno dei valori che trovi dentro PaymentMethod.java
        entity.setMethod(dto.getMethod() != null ? dto.getMethod() : PaymentMethod.CREDIT_CARD);

        // Gestione prezzo (corretta)
        entity.setTotalPrice(dto.getTotalPrice() != null ? dto.getTotalPrice() : 0.0);

        // Gestione data
        entity.setDate(dto.getDate() != null ? dto.getDate() : LocalDateTime.now());

        // Gestione Utente
        if (dto.getUserId() != null) {
            User user = new User();
            user.setId(dto.getUserId());
            entity.setUser(user);
        } else {
            // Se non passi un utente, questa riga lancerà un errore.
            // Assicurati di inviare sempre un "userId" nel JSON di Postman.
            throw new IllegalArgumentException("Il campo userId è obbligatorio nel DTO!");
        }

        return entity;
    }
}