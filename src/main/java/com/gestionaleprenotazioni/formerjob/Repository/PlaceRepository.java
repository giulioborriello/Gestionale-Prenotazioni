package com.gestionaleprenotazioni.formerjob.Repository;

import com.gestionaleprenotazioni.formerjob.Model.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
//MODIFIY REPOSITORY DOVREBBE ANDR BENE
@Repository
public interface PlaceRepository extends JpaRepository<Place, Integer> {

    // posti per nome/codice fratm
    List<Place> findByCode(String code);

    //posti liberi ooccupati
    List<Place> findByStatus(boolean status);

    //tutti i posti di un evento
    List<Place> findByEventId(Integer eventId);
}