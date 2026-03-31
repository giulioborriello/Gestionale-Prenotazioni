package com.gestionaleprenotazioni.formerjob.Service;

import com.gestionaleprenotazioni.formerjob.Mapper.Mapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public abstract class AbstractService<ENTITY,DTO> implements ServiceDTO<DTO> {


    protected JpaRepository<ENTITY,Integer> repository;
    protected Mapper<DTO,ENTITY> mapper;

    protected AbstractService(JpaRepository<ENTITY, Integer> repository,
                              Mapper<DTO, ENTITY> mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public DTO insert(DTO dto) {
        return mapper.toDTO(repository.save(mapper.toEntity(dto)));
    }

    @Override
    public Iterable<DTO> getAll() {
        return mapper.toDTOList(repository.findAll());
    }

    @Override
    public DTO read(Integer id) {
        ENTITY entity = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource not found with id: " + id));
        return mapper.toDTO(entity);
    }

    @Override
    public DTO update(DTO dto) {
        return mapper.toDTO(repository.save(mapper.toEntity(dto)));
    }

    @Override
    public void delete(Integer id) {
        repository.deleteById(id);
    }
}
