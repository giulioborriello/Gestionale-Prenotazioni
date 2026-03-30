package com.gestionaleprenotazioni.formerjob.Service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Optional;
import com.gestionaleprenotazioni.formerjob.Model.Place;
import com.gestionaleprenotazioni.formerjob.Repository.PlaceRepository;

@Service
public class PlaceService {

    @Autowired
    private PlaceRepository placeRepository;

    //ottieni tutti i posti
    public List<Place> getAllPlaces() {
        return placeRepository.findAll();
    }

    //ottieni posto per id
    public Optional<Place> getPlaceById(Integer id) {
        return placeRepository.findById(id);
    }

    //crea un nuovo posto
    public Place createPlace(Place place) {
        return placeRepository.save(place);
    }

    //aggiorna lo stato di 1 posto
    public Place updatePlaceStatus(Integer id, boolean status) {
        Place place = placeRepository.findById(id).orElseThrow(() -> new RuntimeException("Place non trovato"));
        place.setStatus(status);
        return placeRepository.save(place);
    }

    //elimina un posto
    public void deletePlace(Integer id) {
        placeRepository.deleteById(id);
    }
}