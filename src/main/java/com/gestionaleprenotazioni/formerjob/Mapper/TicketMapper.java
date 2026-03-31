package com.gestionaleprenotazioni.formerjob.Mapper;

import com.gestionaleprenotazioni.formerjob.Dto.TicketDto;
import com.gestionaleprenotazioni.formerjob.Model.Ticket;
import org.springframework.stereotype.Component;

@Component
public class TicketMapper extends AbstractMapper<TicketDto, Ticket> {

    @Override
    public Ticket toEntity(TicketDto ticketDto) {
        if (ticketDto == null) {
            return null;
        }

        Ticket ticket = new Ticket();
        ticket.setId(ticketDto.getTicketId());
        ticket.setName(ticketDto.getName());
        ticket.setSurname(ticketDto.getSurname());
        ticket.setPrice(ticketDto.getPrice());
        ticket.setCreationDate(ticketDto.getCreationDate());
        return ticket;
    }

    @Override
    public TicketDto toDTO(Ticket ticket) {
        if (ticket == null) {
            return null;
        }

        TicketDto dto = new TicketDto();
        dto.setTicketId(ticket.getId());
        dto.setName(ticket.getName());
        dto.setSurname(ticket.getSurname());
        dto.setPrice(ticket.getPrice());
        dto.setCreationDate(ticket.getCreationDate());

        if (ticket.getCart() != null) {
            dto.setCartId(ticket.getCart().getId());
        }
        if (ticket.getPlace() != null) {
            dto.setPlaceId(ticket.getPlace().getId());
        }
        if (ticket.getUser() != null) {
            dto.setUserId(ticket.getUser().getId());
        }
        if (ticket.getEvent() != null) {
            dto.setEventId(ticket.getEvent().getId());
        }

        return dto;
    }
}
