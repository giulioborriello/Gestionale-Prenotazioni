package com.gestionaleprenotazioni.formerjob.Repository;

import com.gestionaleprenotazioni.formerjob.Model.Event;
import com.gestionaleprenotazioni.formerjob.Model.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {

    // Metodo che ricerca eventi per nome
    List<Event> findByNameContainingIgnoreCase(String name);

    // Metodo che ricerca eventi per descrizione
    List<Event> findByDescriptionContainingIgnoreCase(String description);

    // Metodo che ricerca eventi per tipologia di evento
    List<Event> findByType(Type type);

    // Metodo che ricerca eventi per il luogo dell'evento
    List<Event> findByLocationContainingIgnoreCase(String location);

    // Metodo che ricerca eventi tra due date
    List<Event> findByDateBetween(Date startDate, Date endDate);

    // Metodo che ricerca eventi dopo una certa data
    List<Event> findByDateAfter(Date data);

    // Metodo che ricerca eventi prima di una certa data
    List<Event> findByDateBefore(Date data);

    // Metodo che ricerca eventi per numero di biglietti venduti
    List<Event> findBySelledTickets(Integer selledTickets);

    // Metodo che ricerca eventi con meno di X biglietti venduti
    List<Event> findBySelledTicketsLessThan(Integer selledTickets);

    // Metodo che ricerca eventi con più di X biglietti venduti
    List<Event> findBySelledTicketsGreaterThan(Integer selledTickets);

    // Metodo che ricerca eventi per il costo del biglietto
    List<Event> findByTicketPrice(Double ticketPrice);

    // Metodo che ricerca eventi più remunerativi e più recenti
    List<Event> findTop5ByOrderBySelledTicketsDescDateDesc();

    // 🔹 Biglietti venduti di un singolo evento
    @Query("SELECT e.selledTickets FROM Event e WHERE e.id = :eventId")
    Integer getSelledTicketsByEventId(@Param("eventId") Integer eventId);

    // 🔹 Biglietti disponibili di un singolo evento
    @Query("SELECT (e.maxTickets - e.selledTickets) FROM Event e WHERE e.id = :eventId")
    Integer getAvailableTicketsByEventId(@Param("eventId") Integer eventId);
}
