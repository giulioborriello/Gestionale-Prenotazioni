package com.gestionaleprenotazioni.formerjob.Mapper;

import com.gestionaleprenotazioni.formerjob.Dto.PlaceDto;
import com.gestionaleprenotazioni.formerjob.Model.Place;
import org.springframework.stereotype.Component;

//mapper per convertire placemodel in placedto
@Component
public class PlaceMapper extends AbstractMapper<PlaceDto, Place> {

    //converte dto in entity
    @Override
    public Place toEntity(PlaceDto dto) {
        if (dto == null) return null;

        Place place = new Place();
        place.setId(dto.getId());
        place.setNome(dto.getNome());
        place.setCode(dto.getCode());
        place.setStatus(dto.isStatus());
        place.setType(dto.getType());

        //event viene collegato nel controller e service
        return place;
    }

    //converte entity in dto
    @Override
    public PlaceDto toDTO(Place place) {
        if (place == null) return null;

        //prendo id evento se presente
        Integer eventId = (place.getEvent() != null) ? place.getEvent().getId() : null;

        return new PlaceDto(
                place.getId(),
                place.getNome(),
                place.getCode(),
                place.isStatus(),
                place.getType(),
                eventId
        );
    }
}