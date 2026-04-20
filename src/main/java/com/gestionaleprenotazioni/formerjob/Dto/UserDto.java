package com.gestionaleprenotazioni.formerjob.Dto;

import com.gestionaleprenotazioni.formerjob.Model.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;
/**
 * Data Transfer Object (DTO) per la rappresentazione dei dati di un utente.
 *
 * Questo DTO è utilizzato per trasferire i dati dell'utente tra i layer
 * dell'applicazione (Controller, Service, Repository) senza esporre
 * direttamente l'entità JPA.
 *
 * Contiene informazioni personali dell'utente, credenziali e riferimenti
 * ai biglietti e pagamenti associati.
 *
 * @author Gestionale Prenotazioni
 * @version 1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto
{
    /**
     * Identificatore univoco dell'utente.
     * Corrisponde all'ID dell'entità User nel database.
     */
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
     * Utilizzato per identificare univocamente l'utente nel sistema.
     */
    private String email;
    /**
     * Password dell'utente per l'autenticazione.
     * Deve essere crittografata prima di essere memorizzata nel database.
     */
    private String password;
    /**
     * Data di nascita dell'utente.
     * Utilizzata per verifiche di età e validazioni.
     */
    private Date DateOfBirth;
    /**
     * Ruolo dell'utente nel sistema.
     * Determina i permessi e le autorizzazioni disponibili per l'utente
     * (es: ADMIN, USER, MODERATOR).
     */
    private Role role;
    /**
     * Lista degli identificatori dei biglietti acquisiti o prenotati dall'utente.
     * Relazione uno-a-molti con l'entità Ticket.
     * Contiene solo gli ID per ridurre la dimensione del DTO.
     */
    private List<Integer> ticketIds;
    /**
     * Lista degli identificatori dei pagamenti effettuati dall'utente.
     * Relazione uno-a-molti con l'entità Payment.
     * Contiene solo gli ID per ridurre la dimensione del DTO.
     */
    private List<Integer> paymentIds;
}
