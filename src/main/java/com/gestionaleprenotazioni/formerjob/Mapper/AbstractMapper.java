package com.gestionaleprenotazioni.formerjob.Mapper;

import com.gestionaleprenotazioni.formerjob.Dto.EventDto;
import com.gestionaleprenotazioni.formerjob.Model.Event;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractMapper<DTO, Entity> implements Mapper<DTO, Entity> {
    @Override
    public List<Entity> toEntityList(Iterable<DTO> dtoIterable) {
        List<Entity> list = new ArrayList<>();
        for (DTO dto : dtoIterable) {
            list.add(toEntity(dto));
        }

        return list;
    }

    @Override
    public List<DTO> toDTOList(Iterable<Entity> entityIterable) {
        List<DTO> list = new ArrayList<>();
        for (Entity entity : entityIterable) {
            list.add(toDTO(entity));
        }

        return list;
    }

    // Converte un'entità Event in DTO EventDto
    // ModelMapper copia automaticamente tutti i campi che hanno lo stesso nome e tipo
    public abstract Event toDTO(Event entity);

    // Converte un DTO EventDto in entità Event
    // Anche qui ModelMapper gestisce automaticamente il mapping dei campi
    public abstract EventDto toEntity(EventDto dto);
}
