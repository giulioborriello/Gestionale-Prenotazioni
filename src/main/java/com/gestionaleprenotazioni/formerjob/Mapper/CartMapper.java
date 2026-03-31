package com.gestionaleprenotazioni.formerjob.Mapper;

import com.gestionaleprenotazioni.formerjob.Dto.CartDto;
import com.gestionaleprenotazioni.formerjob.Model.Cart;
import com.gestionaleprenotazioni.formerjob.Model.Payment;
import com.gestionaleprenotazioni.formerjob.Model.Ticket;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CartMapper extends AbstractMapper<CartDto, Cart> {

    @Override
    public Cart toEntity(CartDto dto) {
        if (dto == null) return null;

        Cart entity = new Cart();
        entity.setId(dto.getId());
        entity.setTotalPrice(dto.getTotalPrice());

        return entity;
    }

    @Override
    public CartDto toDTO(Cart entity) {
        if (entity == null) return null;

        CartDto dto = new CartDto();
        dto.setId(entity.getId());
        dto.setTotalPrice(entity.getTotalPrice());


        if (entity.getTickets() != null) {
            List<Integer> ticketIds = entity.getTickets().stream()
                    .map(Ticket::getId)
                    .collect(Collectors.toList());
            dto.setTicketIds(ticketIds);
        }


        if (entity.getPayments() != null) {
            List<Integer> paymentIds = entity.getPayments().stream()
                    .map(Payment::getId)
                    .collect(Collectors.toList());
            dto.setPaymentIds(paymentIds);
        }

        return dto;
    }
}