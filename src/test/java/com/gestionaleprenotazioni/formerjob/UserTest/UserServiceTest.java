package com.gestionaleprenotazioni.formerjob.UserTest;

import com.gestionaleprenotazioni.formerjob.Dto.UserDto;
import com.gestionaleprenotazioni.formerjob.Mapper.UserMapper;
import com.gestionaleprenotazioni.formerjob.Model.User;
import com.gestionaleprenotazioni.formerjob.Repository.UserRepository;
import com.gestionaleprenotazioni.formerjob.Service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;

    @Test
    void findByName()
    {
        String name = "Gennaro";

        User entity = new User();
        UserDto dto = new UserDto();

        when(userRepository.findByName(name)).thenReturn(List.of(entity));
        when(userMapper.toDTOList(List.of(entity))).thenReturn(List.of(dto));

        List<UserDto> result = userService.findByName(name);

        assertThat(result).isEqualTo(List.of(dto));
    }

    @Test
    void findBySurname()
    {
        String surname = "Esposito";

        User entity = new User();
        UserDto dto = new UserDto();

        when(userRepository.findBySurname(surname)).thenReturn(List.of(entity));
        when(userMapper.toDTOList(List.of(entity))).thenReturn(List.of(dto));

        List<UserDto> result = userService.findBySurname(surname);

        assertThat(result).isEqualTo(List.of(dto));
    }

    @Test
    void findByNameAndSurname()
    {
        String name = "Diego";
        String surname = "Maradona";
        
        User entity = new User();
        UserDto dto = new UserDto();

        when(userRepository.findByNameAndSurname(name, surname)).thenReturn(List.of(entity));
        when(userMapper.toDTOList(List.of(entity))).thenReturn(List.of(dto));

        List<UserDto> result = userService.findByNameAndSurname(name, surname);

        assertThat(result).isEqualTo(List.of(dto));
    }

    @Test
    void findByEmail()
    {
        String email = "genny@esposito.it";

        User entity = new User();
        UserDto dto = new UserDto();

        when(userRepository.findByEmail(email)).thenReturn(entity);
        when(userMapper.toDTO(entity)).thenReturn(dto);

        UserDto result = userService.findByEmail(email);

        assertThat(result).isEqualTo(dto);
    }
    
    @Test
    void findBySurnameAndEmail()
    {
        String surname = "Rossi";
        String email = "rossi@libero.it";

        User entity = new User();
        UserDto dto = new UserDto();

        when(userRepository.findBySurnameAndEmail(surname, email)).thenReturn(entity);
        when(userMapper.toDTO(entity)).thenReturn(dto);

        UserDto result = userService.findBySurnameAndEmail(surname, email);

        assertThat(result).isEqualTo(dto);
    }



}
