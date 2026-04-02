package com.gestionaleprenotazioni.formerjob.Mapper;

import com.gestionaleprenotazioni.formerjob.Dto.TicketDto;
import com.gestionaleprenotazioni.formerjob.Model.Event;
import com.gestionaleprenotazioni.formerjob.Model.Ticket;
import com.gestionaleprenotazioni.formerjob.Model.User;
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

        if (ticketDto.getUserId() != null) {
            User user = new User();
            user.setId(ticketDto.getUserId());
            ticket.setUser(user);
        }

        if (ticketDto.getEventId() != null) {
            Event event = new Event();
            event.setId(ticketDto.getEventId());
            ticket.setEvent(event);
        }

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

        if (ticket.getUser() != null) {
            dto.setUserId(ticket.getUser().getId());
        }
        if (ticket.getEvent() != null) {
            dto.setEventId(ticket.getEvent().getId());
        }

        return dto;
    }
}
