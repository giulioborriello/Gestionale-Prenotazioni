package com.gestionaleprenotazioni.formerjob.Service;

import com.gestionaleprenotazioni.formerjob.Dto.UserDto;
import com.gestionaleprenotazioni.formerjob.Mapper.Mapper;
import com.gestionaleprenotazioni.formerjob.Mapper.UserMapper;
import com.gestionaleprenotazioni.formerjob.Model.User;
import com.gestionaleprenotazioni.formerjob.Repository.UserRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService extends AbstractService<User,UserDto>
{
    private final UserMapper userMapper;

    private final UserRepository userRepository;

    public UserService(JpaRepository<User, Integer> repository, Mapper<UserDto,User> converter, UserMapper userMapper, UserRepository userRepository) {
        super(repository, converter);
        this.userMapper = userMapper;
        this.userRepository = userRepository;
    }

    public UserDto findByName(String name)
    {
        UserDto userDto = userMapper.toDTO(userRepository.findByName(name));
        return userDto;
    }

    public UserDto findBySurname(String surname)
    {
        UserDto userDto = userMapper.toDTO(userRepository.findBySurname(surname));
        return userDto;
    }

    public UserDto findByEmail(String email)
    {
        UserDto userDto = userMapper.toDTO(userRepository.findByEmail(email));
        return userDto;
    }

    public UserDto findByTaxCode(String taxCode)
    {
        UserDto userDto = userMapper.toDTO(userRepository.findByTaxCode(taxCode));
        return userDto;
    }

    public UserDto findByNameAndSurname(String name, String surname)
    {
        UserDto UserDto = userMapper.toDTO(userRepository.findByNameAndSurname(name, surname));
        return UserDto;
    }


}
