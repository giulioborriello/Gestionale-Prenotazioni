package com.gestionaleprenotazioni.formerjob.Repository;

import com.gestionaleprenotazioni.formerjob.Model.Event;
import com.gestionaleprenotazioni.formerjob.Model.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {

    // Metodo che ricerca eventi per nome
    Event findByName(String name);

    // Metodo che ricerca eventi per descrizione
    Event findByDescription(String description);

    // Metodo che ricerca eventi per tipologia di evento
    List<Event> findByType(Type type);

    // Metodo che ricerca eventi per il luogo dell'evento
    List<Event> findByLocation(String location);

    // Metodo che ricerca eventi tra due date
    List<Event> findByDateBetween(Date startDate, Date endDate);

    // Metodo che ricerca eventi dopo una certa data
    List<Event> findByDateAfter(Date data);

    // Metodo che ricerca eventi prima di una certa data
    List<Event> findByDateBefore(Date data);
}
