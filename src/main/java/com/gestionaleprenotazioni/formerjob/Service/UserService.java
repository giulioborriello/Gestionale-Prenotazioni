package com.gestionaleprenotazioni.formerjob.Service;

import com.gestionaleprenotazioni.formerjob.Dto.UserDto;
import com.gestionaleprenotazioni.formerjob.Mapper.UserMapper;
import com.gestionaleprenotazioni.formerjob.Model.User;
import com.gestionaleprenotazioni.formerjob.Repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService extends AbstractService<UserDto, User>
{
      UserRepository userRepository;
      UserMapper userMapper;


    public UserDto findByName(String name)
    {
        return userMapper.toDTO(userRepository.findByName(name));
    }

    public UserDto findBySurname(String surname)
    {
        return userMapper.toDTO(userRepository.findBySurname(surname));
    }

    public UserDto findByEmail(String email)
    {
        return userMapper.toDTO(userRepository.findByEmail(email));
    }

    public UserDto findByTaxCode(String taxCode)
    {
        return userMapper.toDTO(userRepository.findByTaxCode(taxCode));
    }

    public UserDto findByNameAndSurname(String name,String surname)
    {
        return userMapper.toDTO(userRepository.findByNameAndSurname(name, surname));
    }




}
