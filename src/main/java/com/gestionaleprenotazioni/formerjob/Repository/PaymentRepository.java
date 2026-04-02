package com.gestionaleprenotazioni.formerjob.Repository;

import com.gestionaleprenotazioni.formerjob.Model.Payment;
import com.gestionaleprenotazioni.formerjob.Model.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {


    // 2. METODI DERIVATI

    // Trova i pagamenti per metodo (es. tutti quelli fatti con PAYPAL)
    List<Payment> findByMethod(PaymentMethod method);

    // Trova i pagamenti effettuati in una certa data
    List<Payment> findByDateBetween(LocalDateTime start, LocalDateTime end);

    // Trova i pagamenti associati a uno specifico utente
    List<Payment> findByUser_Id(Integer userId);

}