package com.gestionaleprenotazioni.formerjob.Service;

import com.gestionaleprenotazioni.formerjob.Dto.PlaceDto;
import com.gestionaleprenotazioni.formerjob.Mapper.PlaceMapper;
import com.gestionaleprenotazioni.formerjob.Model.Place;
import com.gestionaleprenotazioni.formerjob.Repository.PlaceRepository;
import org.springframework.stereotype.Service;
//ultima modifica service dovrebbe andar bene
import java.util.List;

@Service
public class PlaceService extends AbstractService<Place, PlaceDto> {

    private final PlaceRepository placeRepository;
    private final PlaceMapper placeMapper;

    //costruttor
    public PlaceService(PlaceRepository placeRepository, PlaceMapper placeMapper) {
        super(placeRepository, placeMapper);
        this.placeRepository = placeRepository;
        this.placeMapper = placeMapper;
    }

    //per codice nome
    public List<PlaceDto> findByCode(String code) {
        return placeMapper.toDTOList(placeRepository.findByCode(code));
    }

    //post liberi o occupat
    public List<PlaceDto> findByStatus(boolean status) {
        return placeMapper.toDTOList(placeRepository.findByStatus(status));
    }

    //search dei posti di un certo evento
    public List<PlaceDto> findByEventId(Integer eventId) {
        return placeMapper.toDTOList(placeRepository.findByEventId(eventId));
    }
}