package com.gestionaleprenotazioni.formerjob.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;
/**
 * Entità JPA che rappresenta un utente del sistema di prenotazione di eventi.
 *
 * Questa classe modella i dati personali di un utente, le credenziali di accesso,
 * e mantiene le relazioni con i biglietti e i pagamenti associati.
 *
 * @author Gestionale Prenotazioni
 * @version 1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "User", schema = "formerjob")
public class User
{
    /**
     * Identificatore univoco dell'utente.
     * Generato automaticamente dal database come primary key.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     * Nome dell'utente.
     */
    private String name;
    /**
     * Cognome dell'utente.
     */
    private String surname;
    /**
     * Indirizzo email univoco dell'utente.
     * Deve essere unico nel sistema per garantire l'unicità dell'identità.
     */
    @Column(unique = true)
    private String email;
    /**
     * Password dell'utente per l'autenticazione.
     * Deve essere crittografata prima di essere memorizzata nel database.
     */
    private String password;
    /**
     * Data di nascita dell'utente.
     */
    private Date DateOfBirth;
    /**
     * Ruolo dell'utente nel sistema (es: ADMIN, USER, MODERATOR).
     * Determina i permessi e le autorizzazioni disponibili per l'utente.
     */
    private Role role;
    /**
     * Lista di biglietti acquistati o prenotati dall'utente.
     * Relazione uno-a-molti: un utente può possedere più biglietti.
     */
    @OneToMany(mappedBy = "user")
    private List<Ticket> tickets;
    /**
     * Lista di pagamenti effettuati dall'utente.
     * Relazione uno-a-molti: un utente può effettuare più pagamenti.
     */
    @OneToMany(mappedBy = "user")
    private List<Payment> payments;


}
