package com.gestionaleprenotazioni.formerjob.Mapper;

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
}
