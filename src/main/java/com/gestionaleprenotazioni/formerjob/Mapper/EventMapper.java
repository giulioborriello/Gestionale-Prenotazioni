package com.gestionaleprenotazioni.formerjob.Mapper;

import com.gestionaleprenotazioni.formerjob.Dto.EventDto;
import com.gestionaleprenotazioni.formerjob.Dto.TicketDto;
import com.gestionaleprenotazioni.formerjob.Model.Event;
import com.gestionaleprenotazioni.formerjob.Model.Ticket;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class EventMapper extends AbstractMapper<EventDto, Event> {

    // Istanza di ModelMapper utilizzata per mappare automaticamente campi con lo stesso nome
    final private ModelMapper mapper = new ModelMapper();

    // Converte un DTO EventDto in entità Event
    // Anche qui ModelMapper gestisce automaticamente il mapping dei campi
    @Override
    public Event toEntity(EventDto eventDto) {
        return mapper.map(eventDto, Event.class);
    }

    // Converte un'entità Event in DTO EventDto
    // ModelMapper copia automaticamente tutti i campi che hanno lo stesso nome e tipo
    @Override
    public EventDto toDTO(Event event) {
        return mapper.map(event, EventDto.class);
    }

}
