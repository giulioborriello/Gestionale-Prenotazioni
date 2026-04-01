package com.gestionaleprenotazioni.formerjob.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.gestionaleprenotazioni.formerjob.Model.Place;

import java.util.List;

//rep. accedere ai dati dei posti
public interface PlaceRepository extends JpaRepository<Place, Integer> {
	List<Place> findByNome(String nome);
}