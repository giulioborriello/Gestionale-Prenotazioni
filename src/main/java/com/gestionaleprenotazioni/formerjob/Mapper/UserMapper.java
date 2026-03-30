package com.gestionaleprenotazioni.formerjob.Mapper;

import com.gestionaleprenotazioni.formerjob.Dto.UserDto;
import com.gestionaleprenotazioni.formerjob.Model.User;
import jakarta.persistence.Entity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UserMapper extends AbstractMapper<User, UserDto>
{
    final private ModelMapper mapper = new ModelMapper();

    @Override
    public UserDto toEntity(User user) {
        return null;
    }

    @Override
    public User toDTO(UserDto userDto) {
        return null;
    }
}
