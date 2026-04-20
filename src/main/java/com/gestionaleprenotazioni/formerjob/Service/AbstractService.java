package com.gestionaleprenotazioni.formerjob.Service;

import com.gestionaleprenotazioni.formerjob.Mapper.Mapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * Classe base astratta per servizi CRUD generici.
 *
 * <p>Fornisce un'implementazione standard delle operazioni principali
 * (insert, getAll, read, update, delete) usando:</p>
 * <ul>
 *     <li>un {@link JpaRepository} per la persistenza</li>
 *     <li>un {@link Mapper} per conversione Entity <-> DTO</li>
 * </ul>
 *
 * @param <ENTITY> tipo dell'entita persistente
 * @param <DTO> tipo del Data Transfer Object esposto dal servizio
 */
public abstract class AbstractService<ENTITY,DTO> implements ServiceDTO<DTO> {

    /**
     * Repository JPA generico per l'entita gestita dal servizio.
     */
    protected JpaRepository<ENTITY,Integer> repository;

    /**
     * Mapper per conversione tra DTO ed entita.
     */
    protected Mapper<DTO,ENTITY> mapper;

    /**
     * Costruttore protetto usato dai servizi concreti.
     *
     * @param repository repository JPA dell'entita
     * @param mapper mapper DTO/Entity
     */
    protected AbstractService(JpaRepository<ENTITY, Integer> repository,
                              Mapper<DTO, ENTITY> mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    /**
     * Inserisce una nuova risorsa.
     *
     * <p>Converte il DTO in entita, salva a database, quindi riconverte in DTO.</p>
     *
     * @param dto dati della risorsa da inserire
     * @return DTO della risorsa salvata
     */
    @Override
    public DTO insert(DTO dto) {
        return mapper.toDTO(repository.save(mapper.toEntity(dto)));
    }

    /**
     * Recupera tutte le risorse disponibili.
     *
     * @return collezione di DTO
     */
    @Override
    public Iterable<DTO> getAll() {
        return mapper.toDTOList(repository.findAll());
    }

    /**
     * Recupera una risorsa per ID.
     *
     * @param id identificativo della risorsa
     * @return DTO della risorsa trovata
     * @throws ResponseStatusException NOT_FOUND se la risorsa non esiste
     */
    @Override
    public DTO read(Integer id) {
        ENTITY entity = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource not found with id: " + id));
        return mapper.toDTO(entity);
    }

    /**
     * Aggiorna una risorsa esistente.
     *
     * <p>Converte il DTO in entita, salva a database, quindi riconverte in DTO.</p>
     *
     * @param dto dati aggiornati della risorsa
     * @return DTO della risorsa aggiornata
     */
    @Override
    public DTO update(DTO dto) {
        return mapper.toDTO(repository.save(mapper.toEntity(dto)));
    }

    /**
     * Elimina una risorsa per ID.
     *
     * @param id identificativo della risorsa da eliminare
     */
    @Override
    public void delete(Integer id) {
        repository.deleteById(id);
    }
}
