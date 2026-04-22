package com.gestionaleprenotazioni.formerjob.Mapper;

import com.gestionaleprenotazioni.formerjob.Dto.PaymentDto;
import com.gestionaleprenotazioni.formerjob.Model.Payment;
import com.gestionaleprenotazioni.formerjob.Model.PaymentMethod;
import com.gestionaleprenotazioni.formerjob.Model.User;
import com.gestionaleprenotazioni.formerjob.Model.Event; // Assicurati di importare l'entità Event
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

        // Mappatura User
        if (entity.getUser() != null) {
            dto.setUserId(entity.getUser().getId());
        }

        // Mappatura Event (NUOVO)
        if (entity.getEvent() != null) {
            dto.setEventId(entity.getEvent().getId());
        }

        return dto;
    }

    @Override
    public Payment toEntity(PaymentDto dto) {
        if (dto == null) return null;

        Payment entity = new Payment();
        entity.setId(dto.getId());

        // Gestione Metodo
        entity.setMethod(dto.getMethod() != null ? dto.getMethod() : PaymentMethod.CREDIT_CARD);

        // Gestione prezzo
        entity.setTotalPrice(dto.getTotalPrice() != null ? dto.getTotalPrice() : 0.0);

        // Gestione data
        entity.setDate(dto.getDate() != null ? dto.getDate() : LocalDateTime.now());

        // Gestione Utente
        if (dto.getUserId() != null) {
            User user = new User();
            user.setId(dto.getUserId());
            entity.setUser(user);
        } else {
            throw new IllegalArgumentException("Il campo userId è obbligatorio nel DTO!");
        }

        // Gestione Event (NUOVO)
        if (dto.getEventId() != null) {
            Event event = new Event();
            event.setId(dto.getEventId());
            entity.setEvent(event);
        } else {
            // Se l'evento è obbligatorio, manteniamo il controllo
            throw new IllegalArgumentException("Il campo eventId è obbligatorio nel DTO!");
        }

        return entity;
    }
}