package com.gestionaleprenotazioni.formerjob.Repository;

import com.gestionaleprenotazioni.formerjob.Model.Cart;
import com.gestionaleprenotazioni.formerjob.Model.Place;
import com.gestionaleprenotazioni.formerjob.Model.Ticket;
import com.gestionaleprenotazioni.formerjob.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer> {
    List<Ticket> findTicketByNameAndSurname(String name, String surname);
    List<Ticket> findTicketByCreationDate(LocalDateTime creationDate);
    List<Ticket> findTicketByPriceGreaterThanEqual(Double priceIsGreaterThan);
    List<Ticket> findTicketByPriceLessThanEqual(Double priceIsLessThan);

    @Query("SELECT t FROM Ticket t WHERE t.price >= ?1 AND t.price <= ?2")
    List<Ticket> findTicketByPriceRange(Double initialPrice, Double endPrice);

    @Query("SELECT t FROM Ticket t WHERE t.creationDate >= ?1 AND t.creationDate <= ?2")
    List<Ticket> findTicketByDateRange(LocalDateTime startDate, LocalDateTime endDate);
}
