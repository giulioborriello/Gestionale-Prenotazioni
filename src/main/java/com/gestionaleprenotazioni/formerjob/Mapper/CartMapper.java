package com.gestionaleprenotazioni.formerjob.Mapper;

import com.gestionaleprenotazioni.formerjob.Dto.CartDto;
import com.gestionaleprenotazioni.formerjob.Model.Cart;
import com.gestionaleprenotazioni.formerjob.Model.Ticket;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CartMapper implements Converter<Cart, CartDto> {

    @Override
    public CartDto toDTO(Cart entity) {
        if (entity == null) return null;

        CartDto dto = new CartDto();
        dto.setId(entity.getId());
        dto.setTotalPrice(entity.getTotalPrice());

        // Estraiamo l'ID dall'oggetto User
        if (entity.getUser() != null) {
            dto.setUserId(entity.getUser().getId());
        }

        // Estraiamo l'ID dal pagamento se esiste
        if (entity.getPayment() != null) {
            dto.setPaymentId(entity.getPayment().getId());
        }

        // Trasformiamo la lista di oggetti Ticket in una lista di soli Integer (ID)
        if (entity.getTickets() != null) {
            List<Integer> ids = entity.getTickets().stream()
                    .map(Ticket::getId)
                    .collect(Collectors.toList());
            dto.setTicketIds(ids);
        }

        return dto;
    }

    @Override
    public Cart hideToEntity(CartDto dto) {
        // Solitamente la conversione inversa da DTO a Entity per il carrello
        // si gestisce nel Service perché bisogna recuperare gli oggetti dal DB tramite ID
        return null;
    }

    @Override
    public List<CartDto> toDTOList(List<Cart> entities) {
        return entities.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<Cart> toEntityList(List<CartDto> dtos) {
        return dtos.stream().map(this::hideToEntity).collect(Collectors.toList());
    }
}