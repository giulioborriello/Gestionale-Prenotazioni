package com.gestionaleprenotazioni.formerjob.Repository;

import com.gestionaleprenotazioni.formerjob.Model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository Spring Data JPA per l'entita {@link Ticket}.
 *
 * <p>Estende {@link JpaRepository} per fornire le operazioni CRUD standard
 * e dichiara metodi di ricerca specifici basati su naming convention
 * e query JPQL personalizzate.</p>
 */
@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer> {

    /**
     * Cerca ticket per nome e cognome dell'intestatario.
     *
     * @param name nome dell'intestatario
     * @param surname cognome dell'intestatario
     * @return lista di ticket che corrispondono a nome e cognome
     */
    List<Ticket> findTicketByNameAndSurname(String name, String surname);

    /**
     * Cerca ticket per data/ora di creazione esatta.
     *
     * @param creationDate data/ora di creazione da confrontare
     * @return lista di ticket creati nella data/ora indicata
     */
    List<Ticket> findTicketByCreationDate(LocalDateTime creationDate);

    /**
     * Cerca ticket con prezzo maggiore o uguale al valore indicato.
     *
     * @param priceIsGreaterThan soglia minima inclusa
     * @return lista di ticket con prezzo >= soglia
     */
    List<Ticket> findTicketByPriceGreaterThanEqual(Double priceIsGreaterThan);

    /**
     * Cerca ticket con prezzo minore o uguale al valore indicato.
     *
     * @param priceIsLessThan soglia massima inclusa
     * @return lista di ticket con prezzo <= soglia
     */
    List<Ticket> findTicketByPriceLessThanEqual(Double priceIsLessThan);

    /**
     * Cerca ticket in un intervallo di prezzo (estremi inclusi).
     *
     * @param initialPrice prezzo minimo dell'intervallo
     * @param endPrice prezzo massimo dell'intervallo
     * @return lista di ticket con prezzo compreso tra i due valori
     */
    @Query("SELECT t FROM Ticket t WHERE t.price >= ?1 AND t.price <= ?2")
    List<Ticket> findTicketByPriceRange(Double initialPrice, Double endPrice);

    /**
     * Cerca ticket in un intervallo temporale di creazione (estremi inclusi).
     *
     * @param startDate data/ora iniziale dell'intervallo
     * @param endDate data/ora finale dell'intervallo
     * @return lista di ticket con creationDate compresa tra startDate ed endDate
     */
    @Query("SELECT t FROM Ticket t WHERE t.creationDate >= ?1 AND t.creationDate <= ?2")
    List<Ticket> findTicketByDateRange(LocalDateTime startDate, LocalDateTime endDate);

    List<Ticket> findTicketByUser_Id(Integer userId);



}
