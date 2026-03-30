package com.gestionaleprenotazioni.formerjob.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.gestionaleprenotazioni.formerjob.Model.Place;

//rep. accedere ai dati dei posti
public interface PlaceRepository extends JpaRepository<Place, Integer> {
}