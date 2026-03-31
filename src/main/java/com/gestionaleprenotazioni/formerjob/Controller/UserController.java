package com.gestionaleprenotazioni.formerjob.Controller;

import com.gestionaleprenotazioni.formerjob.Dto.UserDto;
import com.gestionaleprenotazioni.formerjob.Service.UserService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("User")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController
{
    private UserService service;

    @GetMapping("/findByNome")
    public UserDto FindByName(String name)
    {
        return service.findByName(name);
    }

    @GetMapping("/findBySurname")
    public UserDto FindBySurname(String surname)
    {
        return service.findBySurname(surname);
    }

    @GetMapping("/findByEmail")
    public UserDto FindByEmail(String email)
    {
        return service.findByEmail(email);
    }

    @GetMapping("/findByTaxCode")
    public UserDto FindByTaxCode(String taxCode)
    {
        return service.findByTaxCode(taxCode);
    }

    @GetMapping("/findByNameAndSurname")
    public UserDto FindByNameAndSurname(String name, String surname)
    {
        return service.findByNameAndSurname(name, surname);
    }

    @GetMapping("/findBySurnameAndEmail")
    public UserDto FindBySurnameAndEmail(String surname,String email)
    {
        return service.findBySurnameAndEmail(surname,email);
    }







}
