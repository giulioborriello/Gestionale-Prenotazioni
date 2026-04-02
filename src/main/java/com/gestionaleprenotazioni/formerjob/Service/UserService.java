package com.gestionaleprenotazioni.formerjob.Service;

import com.gestionaleprenotazioni.formerjob.Dto.UserDto;
import com.gestionaleprenotazioni.formerjob.Mapper.Mapper;
import com.gestionaleprenotazioni.formerjob.Mapper.UserMapper;
import com.gestionaleprenotazioni.formerjob.Model.User;
import com.gestionaleprenotazioni.formerjob.Repository.UserRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<UserDto> findByName(String name)
    {
        return userMapper.toDTOList(userRepository.findByName(name));
    }

    public List<UserDto> findBySurname(String surname)
    {
        return userMapper.toDTOList(userRepository.findBySurname(surname));
    }

    public UserDto findByEmail(String email)
    {
        return userMapper.toDTO(userRepository.findByEmail(email));
    }

    public List<UserDto> findByNameAndSurname(String name, String surname)
    {
        return userMapper.toDTOList(userRepository.findByNameAndSurname(name, surname));
    }

    public UserDto findBySurnameAndEmail(String surname,String email)
    {
        return userMapper.toDTO(userRepository.findBySurnameAndEmail(surname,email));
    }
}
