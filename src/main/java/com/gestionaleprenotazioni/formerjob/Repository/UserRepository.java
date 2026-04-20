package com.gestionaleprenotazioni.formerjob.Repository;

import com.gestionaleprenotazioni.formerjob.Model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * Interfaccia repository per la gestione delle entità User.
 * Estende JpaRepository per fornire operazioni CRUD di base e metodi di query personalizzati
 * per trovare utenti basati su vari criteri come nome, cognome e email.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer>
{
        /**
         * Trova una lista di utenti per nome.
         *
         * @param name il nome da cercare
         * @return una lista di utenti con il nome specificato
         */
        List<User> findByName(String name);
        /**
         * Trova una lista di utenti per cognome.
         *
         * @param surname il cognome da cercare
         * @return una lista di utenti con il cognome specificato
         */
        List<User> findBySurname(String surname);
        /**
         * Trova un singolo utente per indirizzo email.
         *
         * @param email l'indirizzo email da cercare
         * @return l'utente con l'email specificata, o null se non trovato
         */
        User findByEmail(String email);
        /**
         * Trova un singolo utente per cognome e indirizzo email.
         *
         * @param surname il cognome da cercare
         * @param email l'indirizzo email da cercare
         * @return l'utente con il cognome e l'email specificati, o null se non trovato
         */
        User findBySurnameAndEmail(String surname,String email);

        /**
         * Trova una lista di utenti per nome e cognome.
         *
         * @param name il nome da cercare
         * @param surname il cognome da cercare
         * @return una lista di utenti con il nome e il cognome specificati
         */
        List<User> findByNameAndSurname(String name, String surname);
}
