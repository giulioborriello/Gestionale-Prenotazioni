package com.gestionaleprenotazioni.formerjob.Repository;

import com.gestionaleprenotazioni.formerjob.Model.Cart;
import com.gestionaleprenotazioni.formerjob.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {

    // 1. QUERY JPQL: Recupera il carrello tramite l'ID dell'utente
    @Query("SELECT c FROM Cart c WHERE c.user.id = :userId")
    Optional<Cart> findByUserIdJPQL(@Param("userId") Integer userId);

    // 2. METODI DERIVATI

    // Trova il carrello di un utente specifico
    Optional<Cart> findByUser(User user);

    // Trova tutti i carrelli con un prezzo totale superiore a una certa cifra
    List<Cart> findByTotalPriceGreaterThan(Double price);

    List<Cart> findByPaymentIsNull();

    // Trova i carrelli che contengono un pagamento con un certo ID
    // era findByPaymentId() — ora payments è una lista
    List<Cart> findByPaymentsId(Integer paymentId);
}