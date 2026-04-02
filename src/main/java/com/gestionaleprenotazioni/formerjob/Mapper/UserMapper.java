package com.gestionaleprenotazioni.formerjob.Mapper;

import com.gestionaleprenotazioni.formerjob.Dto.UserDto;
import com.gestionaleprenotazioni.formerjob.Model.User;
import jakarta.persistence.Entity;

//dobbiamo aggiungere in converter
public class UserMapper
{
   final private Modelmapper mapper = new ModelMapper();

    @Override
    public UserDto toDTO(User entity) { return mapper.map(entity, UserDto.class); }
    @Override
    public Entity toEntity(UserDto dto) { return mapper.map(dto, User.class);}



}
