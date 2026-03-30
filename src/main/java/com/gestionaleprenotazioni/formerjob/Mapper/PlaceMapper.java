package com.gestionaleprenotazioni.formerjob.Mapper;
import com.gestionaleprenotazioni.formerjob.Model.Event;
import com.gestionaleprenotazioni.formerjob.Dto.PlaceDto;
import com.gestionaleprenotazioni.formerjob.Model.Place;


public class PlaceMapper {

    // Converte Place in PlaceDTO
    public static PlaceDto toDTO(Place place) {
        if (place == null) return null;
        Integer eventId = (place.getEvent() != null) ? place.getEvent().getId() : null;
        return new PlaceDto(
                place.getId(),
                place.getCode(),
                place.isStatus(),
                place.getType(),
                eventId
        );
    }

    // Converte PlaceDTO in Place, *serve l'oggetto Event per collegamento*
    public static Place fromDTO(PlaceDto dto, Event event) {
        if (dto == null) return null;
        Place place = new Place();
        place.setId(dto.getId());
        place.setCode(dto.getCode());
        place.setStatus(dto.isStatus());
        place.setEvent(event); // collega l'evento
        return place;
    }
}