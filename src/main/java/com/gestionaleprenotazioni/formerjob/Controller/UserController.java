package com.gestionaleprenotazioni.formerjob.Controller;

import com.gestionaleprenotazioni.formerjob.Dto.UserDto;
import com.gestionaleprenotazioni.formerjob.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
// Segnala a Spring che questa è una REST API Controller
// Converte automaticamente i risultati in JSON/XML per le HTTP Response
@RestController
// Base path per tutte le rotte di questo controller
// Es: http://localhost:8080/User/findByName?name=Mario
@RequestMapping("User")
// CORS: Permette richieste AJAX da Angular (localhost:4200)
// Necessario per sviluppo frontend/backend separati
@CrossOrigin(origins = "http://localhost:4200")
public class UserController
{
    // Dependency Injection: Spring inietta automaticamente UserService
    @Autowired
    // (constructor injection sarebbe preferibile per immutabilità)
    private UserService service;
    // GET /User/findByName?name=Mario
    // Query param 'name' viene automaticamente bindato dal parametro metodo
    @GetMapping("/findByName")
    // Delega al Service layer (business logic + DTO conversion)
    public List<UserDto> findByName(String name)
    {
        return service.findByName(name);
    }
    // GET /User/findBySurname?surname=Rossi
    @GetMapping("/findBySurname")
    public List<UserDto> FindBySurname(String surname)
    {
        return service.findBySurname(surname);
    }
    // GET /User/findByEmail?email=mario@test.it
    @GetMapping("/findByEmail")
    // Restituisce singolo utente (200 OK o eccezione se non trovato)
    public UserDto FindByEmail(String email)
    {
        return service.findByEmail(email);
    }

    @GetMapping("/findByNameAndSurname")
    public List<UserDto> FindByNameAndSurname(String name, String surname)
    {
        return service.findByNameAndSurname(name, surname);
    }

    @GetMapping("/findBySurnameAndEmail")
    public UserDto FindBySurnameAndEmail(String surname,String email)
    {
        return service.findBySurnameAndEmail(surname,email);
    }
}
