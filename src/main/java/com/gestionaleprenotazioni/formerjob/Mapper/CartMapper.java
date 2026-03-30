package com.gestionaleprenotazioni.formerjob.Mapper;

import com.gestionaleprenotazioni.formerjob.Dto.CartDto;
import com.gestionaleprenotazioni.formerjob.Model.Cart;
import com.gestionaleprenotazioni.formerjob.Model.Ticket;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CartMapper extends AbstractMapper<CartDto, Cart> {


    @Override
    public CartDto toDTO(Cart entity) {
        // Se l'oggetto dal database non esiste, restituiamo null per evitare errori
        if (entity == null) return null;

        CartDto dto = new CartDto();

        // Mappiamo i campi semplici
        dto.setId(entity.getId());
        dto.setTotalPrice(entity.getTotalPrice());

        // MAPPING USER: Estraiamo solo l'ID numerico dall'oggetto User completo
        if (entity.getUser() != null) {
            dto.setUserId(entity.getUser().getId());
        }

        // MAPPING PAYMENT: Se il carrello ha un pagamento, passiamo l'ID al DTO
        if (entity.getPayment() != null) {
            dto.setPaymentId(entity.getPayment().getId());
        }


        if (entity.getTickets() != null) {
            List<Integer> ids = entity.getTickets().stream()
                    .map(Ticket::getId)
                    .collect(Collectors.toList());
            dto.setTicketIds(ids);
        }

        return dto;
    }

    @Override
    public Cart toEntity(CartDto dto) {
        if (dto == null) return null;

        Cart entity = new Cart();


        entity.setId(dto.getId());

        entity.setTotalPrice(dto.getTotalPrice());


        return entity;
    }


}