package com.gestionaleprenotazioni.formerjob.Service;

import com.gestionaleprenotazioni.formerjob.Dto.PlaceDto;
import com.gestionaleprenotazioni.formerjob.Mapper.PlaceMapper;
import com.gestionaleprenotazioni.formerjob.Model.Place;
import com.gestionaleprenotazioni.formerjob.Repository.PlaceRepository;
import org.springframework.stereotype.Service;

//service per gestire la logica dei posti
@Service
public class PlaceService extends AbstractService<Place, PlaceDto> {

    //repository per accesso ai dati
    private final PlaceRepository placeRepository;

    //mapper per conversione dto - entity
    private final PlaceMapper placeMapper;

    //costruttore
    public PlaceService(PlaceRepository placeRepository, PlaceMapper placeMapper) {
        super(placeRepository, placeMapper);
        this.placeRepository = placeRepository;
        this.placeMapper = placeMapper;
    }

    //se aggiungo i metodi personalizz. confrontarmi con il team
}