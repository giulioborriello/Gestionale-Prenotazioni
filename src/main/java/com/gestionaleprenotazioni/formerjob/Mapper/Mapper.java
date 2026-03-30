package com.gestionaleprenotazioni.formerjob.Mapper;

import java.util.List;

public interface Mapper<DTO, Entity> {
    public Entity toEntity(DTO dto);
    public DTO toDTO(Entity entity);
    public List<Entity> toEntityList(Iterable<DTO> dtoIterable);
    public List<DTO> toDTOList(Iterable<Entity> entityIterable);
}
