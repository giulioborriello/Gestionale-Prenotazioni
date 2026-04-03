package com.gestionaleprenotazioni.formerjob.Repository;

import com.gestionaleprenotazioni.formerjob.Model.Payment;
import com.gestionaleprenotazioni.formerjob.Model.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository per la gestione della persistenza dell'entità {@link Payment}.
 * <p>
 * Fornisce l'accesso alla tabella {@code payment} su PostgreSQL, ereditando
 * le operazioni CRUD standard da {@link JpaRepository}.
 * </p>
 */
@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {


    /**
     * Filtra i pagamenti in base al metodo di pagamento utilizzato.
     * * @param method Il {@link PaymentMethod} da ricercare.
     * @return Lista dei pagamenti corrispondenti; lista vuota se nessun risultato trovato.
     */
    List<Payment> findByMethod(PaymentMethod method);

    /**
     * Ricerca i pagamenti effettuati all'interno di un intervallo temporale.
     * * @param start Data e ora di inizio intervallo (inclusa).
     * @param end Data e ora di fine intervallo (inclusa).
     * @return Lista dei pagamenti registrati nel periodo indicato.
     */
    List<Payment> findByDateBetween(LocalDateTime start, LocalDateTime end);

    /**
     * Recupera lo storico dei pagamenti associati a un determinato utente.
     * * @param userId Identificativo univoco dell'utente (Foreign Key).
     * @return Lista dei pagamenti effettuati dall'utente specificato.
     */
    List<Payment> findByUser_Id(Integer userId);

}