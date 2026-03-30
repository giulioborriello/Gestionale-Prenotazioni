package com.gestionaleprenotazioni.formerjob.Mapper;

import com.gestionaleprenotazioni.formerjob.Dto.EventDto;
import com.gestionaleprenotazioni.formerjob.Model.Event;
import org.springframework.stereotype.Component;

@Component
public class EventMapper extends AbstractConverter<Event, EventDto> {

    // Istanza di ModelMapper utilizzata per mappare automaticamente campi con lo stesso nome
    final private ModelMapper mapper = new ModelMapper();

    // Converte un'entità Event in DTO EventDto
    // ModelMapper copia automaticamente tutti i campi che hanno lo stesso nome e tipo
    @Override
    public EventDto toDTO(Event entity) { return mapper.map(entity, EventDto.class); }
}
