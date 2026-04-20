package com.gestionaleprenotazioni.formerjob.Mapper;

import com.gestionaleprenotazioni.formerjob.Dto.EventDto;
import com.gestionaleprenotazioni.formerjob.Model.Event;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementazione astratta base dell'interfaccia {@link Mapper}.
 *
 * <p>Fornisce la logica comune per convertire collezioni di oggetti
 * tra DTO ed Entity, delegando la conversione del singolo elemento
 * ai metodi concreti {@code toEntity(...)} e {@code toDTO(...)}
 * implementati nelle classi figlie.</p>
 *
 * @param <DTO> tipo del Data Transfer Object
 * @param <Entity> tipo dell'entita di dominio/persistenza
 */
public abstract class AbstractMapper<DTO, Entity> implements Mapper<DTO, Entity> {

    /**
     * Converte una collezione iterabile di DTO in una lista di Entity.
     *
     * <p>Per ogni elemento dell'iterabile invoca {@code toEntity(dto)}
     * (implementato nel mapper concreto) e aggiunge il risultato alla lista finale.</p>
     *
     * @param dtoIterable insieme/iterabile di DTO da convertire
     * @return lista di Entity convertite
     */
    @Override
    public List<Entity> toEntityList(Iterable<DTO> dtoIterable) {
        List<Entity> list = new ArrayList<>();
        for (DTO dto : dtoIterable) {
            list.add(toEntity(dto));
        }

        return list;
    }

    /**
     * Converte una collezione iterabile di Entity in una lista di DTO.
     *
     * <p>Per ogni elemento dell'iterabile invoca {@code toDTO(entity)}
     * (implementato nel mapper concreto) e aggiunge il risultato alla lista finale.</p>
     *
     * @param entityIterable insieme/iterabile di Entity da convertire
     * @return lista di DTO convertiti
     */
    @Override
    public List<DTO> toDTOList(Iterable<Entity> entityIterable) {
        List<DTO> list = new ArrayList<>();
        for (Entity entity : entityIterable) {
            list.add(toDTO(entity));
        }

        return list;
    }

}
