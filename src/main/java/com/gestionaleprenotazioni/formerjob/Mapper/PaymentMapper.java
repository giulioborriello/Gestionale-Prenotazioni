package com.gestionaleprenotazioni.formerjob.Mapper;

import com.gestionaleprenotazioni.formerjob.Dto.PaymentDto;
import com.gestionaleprenotazioni.formerjob.Model.Payment;
import com.gestionaleprenotazioni.formerjob.Model.User;
import org.springframework.stereotype.Component;

/**
 * Componente Mapper per la conversione tra {@link Payment} e {@link PaymentDto}.
 * <p>
 * Implementa la logica di trasformazione necessaria per disaccoppiare lo strato
 * di persistenza (Database) dallo strato di presentazione (API).
 * Estende {@link AbstractMapper} per garantire uniformità nelle conversioni del progetto.
 * </p>
 */
@Component
public class PaymentMapper extends AbstractMapper<PaymentDto, Payment> {

    /**
     * Converte un'entità {@link Payment} in un oggetto {@link PaymentDto}.
     * <p>
     * Durante la conversione, estrae l'ID dall'oggetto {@link User} associato
     * e lo assegna al campo {@code userId} del DTO per minimizzare il payload.
     * </p>
     * * @param entity L'entità proveniente dal database.
     * @return Il DTO popolato con i dati dell'entità; {@code null} se l'input è null.
     */
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

    /**
     * Converte un oggetto {@link PaymentDto} in un'entità {@link Payment}.
     * <p>
     * Per gestire la relazione con l'utente, crea un'istanza "proxy" di {@link User}
     * impostando solo l'ID fornito dal DTO. Questa entità è pronta per essere
     * processata dallo strato di persistenza.
     * </p>
     * * @param dto Il DTO proveniente dalla richiesta API.
     * @return L'entità popolata con i dati del DTO; {@code null} se l'input è null.
     */
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