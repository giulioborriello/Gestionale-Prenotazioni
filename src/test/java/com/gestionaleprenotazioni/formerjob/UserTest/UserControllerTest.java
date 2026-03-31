package com.gestionaleprenotazioni.formerjob.UserTest;


import com.gestionaleprenotazioni.formerjob.Controller.UserController;
import com.gestionaleprenotazioni.formerjob.Dto.UserDto;
import com.gestionaleprenotazioni.formerjob.Service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest
{
    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Test
    void shouldFindByName()
    {
        String name = "Gennaro";
        UserDto dto = new UserDto();
        dto.setName(name);

        when(userService.findByName(name)).thenReturn(dto);

        UserDto result = userController.FindByName(name);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo(name);

        verify(userService).findByName(name);
    }

    @Test
    void shouldFindBySurname()
    {
        String surname ="Esposito";
        UserDto dto = new UserDto();
        dto.setSurname(surname);

        when(userService.findBySurname(surname)).thenReturn(dto);

        UserDto result = userController.FindBySurname(surname);

        assertThat(result).isNotNull();
        assertThat(result.getSurname()).isEqualTo(surname);

        verify(userService).findBySurname(surname);
    }

@   Test
    void shouldFindByEmail()
    {
        String email ="gennyesp@gmail.com";
        UserDto dto = new UserDto();
        dto.setEmail(email);

        when(userService.findByEmail(email)).thenReturn(dto);

        UserDto result = userController.FindByEmail(email);

        assertThat(result).isNotNull();
        assertThat(result.getEmail()).isEqualTo(email);

        verify(userService).findByEmail(email);
    }

    @Test
    void shouldFindByTaxCode() {
        String taxCode = "GNNESP99A01F839K";
        UserDto dto = new UserDto();
        dto.setTaxCode(taxCode);

        when(userService.findByTaxCode(taxCode)).thenReturn(dto);
        UserDto result = userController.FindByTaxCode(taxCode);

        assertThat(result).isNotNull();
        assertThat(result.getTaxCode()).isEqualTo(taxCode);

        verify(userService).findByTaxCode(taxCode);

    }

    @Test
    void shouldFindByNameAndSurname()
    {
        String name = "Massimo";
        String surname = "Sturato";
        UserDto dto = new UserDto();
        dto.setName(name);
        dto.setSurname(surname);

        when(userService.findByNameAndSurname(name, surname)).thenReturn(dto);
        UserDto result = userController.FindByNameAndSurname(name, surname);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo(name);
        assertThat(result.getSurname()).isEqualTo(surname);

        verify(userService).findByNameAndSurname(name, surname);
    }

    @Test

    void shouldFindBySurnameAndEmail() {
        String surname = "Sturato";
        String email = "sturato@proton.it";
        UserDto dto = new UserDto();
        dto.setSurname(surname);
        dto.setEmail(email);

        when(userService.findBySurnameAndEmail(surname, email)).thenReturn(dto);
        UserDto result = userController.FindBySurnameAndEmail(surname, email);

        assertThat(result).isNotNull();
        assertThat(result.getSurname()).isEqualTo(surname);
        assertThat(result.getEmail()).isEqualTo(email);

        verify(userService).findBySurnameAndEmail(surname, email);
    }


}
