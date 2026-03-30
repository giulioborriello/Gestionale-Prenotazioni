package com.gestionaleprenotazioni.formerjob.Mapper;

import com.gestionaleprenotazioni.formerjob.Dto.UserDto;
import com.gestionaleprenotazioni.formerjob.Model.User;
import jakarta.persistence.Entity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UserMapper extends AbstractMapper<UserDto, User>
{
    final private ModelMapper mapper = new ModelMapper();

    @Override
    public UserDto toDTO(User entity) { return mapper.map(entity, UserDto.class); }

    @Override
    public User toEntity(UserDto dto) { return mapper.map(dto, User.class);}
}
