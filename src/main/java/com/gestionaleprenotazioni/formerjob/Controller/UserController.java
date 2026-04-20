package com.gestionaleprenotazioni.formerjob.Controller;

import com.gestionaleprenotazioni.formerjob.Dto.UserDto;
import com.gestionaleprenotazioni.formerjob.Service.UserService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller REST per operazioni di ricerca sugli utenti.
 *
 * <p>Espone endpoint sotto il path base {@code /User} e delega la logica
 * al servizio {@link UserService}.</p>
 */
@RestController
@RequestMapping("User")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController
{
    /**
     * Servizio applicativo per ricerca utenti.
     */
    private final UserService service;

    /**
     * Costruttore con dependency injection del service.
     *
     * @param service servizio utenti
     */
    public UserController(UserService service) {
        this.service = service;
    }

    /**
     * Cerca utenti per nome.
     *
     * <p>Endpoint: {@code GET /User/findByName}</p>
     *
     * @param name nome da cercare
     * @return lista di utenti che corrispondono al nome
     */
    @GetMapping("/findByName")
    public List<UserDto> findByName(String name)
    {
        return service.findByName(name);
    }

    /**
     * Cerca utenti per cognome.
     *
     * <p>Endpoint: {@code GET /User/findBySurname}</p>
     *
     * @param surname cognome da cercare
     * @return lista di utenti che corrispondono al cognome
     */
    @GetMapping("/findBySurname")
    public List<UserDto> FindBySurname(String surname)
    {
        return service.findBySurname(surname);
    }

    /**
     * Cerca un utente tramite email.
     *
     * <p>Endpoint: {@code GET /User/findByEmail}</p>
     *
     * @param email email da cercare
     * @return utente trovato
     */
    @GetMapping("/findByEmail")
    public UserDto FindByEmail(String email)
    {
        return service.findByEmail(email);
    }

    /**
     * Cerca utenti per combinazione nome + cognome.
     *
     * <p>Endpoint: {@code GET /User/findByNameAndSurname}</p>
     *
     * @param name nome da cercare
     * @param surname cognome da cercare
     * @return lista di utenti che corrispondono a nome e cognome
     */
    @GetMapping("/findByNameAndSurname")
    public List<UserDto> FindByNameAndSurname(String name, String surname)
    {
        return service.findByNameAndSurname(name, surname);
    }

    /**
     * Cerca un utente per combinazione cognome + email.
     *
     * <p>Endpoint: {@code GET /User/findBySurnameAndEmail}</p>
     *
     * @param surname cognome da cercare
     * @param email email da cercare
     * @return utente trovato
     */
    @GetMapping("/findBySurnameAndEmail")
    public UserDto FindBySurnameAndEmail(String surname,String email)
    {
        return service.findBySurnameAndEmail(surname,email);
    }
}
