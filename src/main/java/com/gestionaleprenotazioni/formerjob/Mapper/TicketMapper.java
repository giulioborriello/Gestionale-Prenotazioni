package com.gestionaleprenotazioni.formerjob.Mapper;

import com.gestionaleprenotazioni.formerjob.Dto.TicketDto;
import com.gestionaleprenotazioni.formerjob.Model.Ticket;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class TicketMapper extends AbstractMapper<TicketDto, Ticket> {

    private final ModelMapper modelMapper = new ModelMapper();

    @Override
    public Ticket toEntity(TicketDto ticketDto) {
        return modelMapper.map(ticketDto, Ticket.class);
    }

    @Override
    public TicketDto toDTO(Ticket ticket) {
        return modelMapper.map(ticket, TicketDto.class);
    }
}
