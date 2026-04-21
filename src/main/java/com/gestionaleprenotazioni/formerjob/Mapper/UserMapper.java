package com.gestionaleprenotazioni.formerjob.Mapper;

import com.gestionaleprenotazioni.formerjob.Dto.UserDto;
import com.gestionaleprenotazioni.formerjob.Model.Payment;
import com.gestionaleprenotazioni.formerjob.Model.Ticket;
import com.gestionaleprenotazioni.formerjob.Model.User;
import org.springframework.stereotype.Component;


@Component
public class UserMapper extends AbstractMapper<UserDto, User>
{
    @Override
    public UserDto toDTO(User entity) {
        if (entity == null) {
            return null;
        }

        UserDto dto = new UserDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setSurname(entity.getSurname());
        dto.setEmail(entity.getEmail());
        dto.setPassword(entity.getPassword());
        dto.setDateOfBirth(entity.getDateOfBirth());
        dto.setRole(entity.getRole());

        if (entity.getTickets() != null) {
            dto.setTicketIds(entity.getTickets().stream()
                    .map(Ticket::getId)
                    .toList());
        }

        if (entity.getPayments() != null) {
            dto.setPaymentIds(entity.getPayments().stream()
                    .map(Payment::getId)
                    .toList());
        }

        return dto;
    }

    @Override
    public User toEntity(UserDto dto) {
        if (dto == null) {
            return null;
        }

        User user = new User();
        user.setId(dto.getId());
        user.setName(dto.getName());
        user.setSurname(dto.getSurname());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setDateOfBirth(dto.getDateOfBirth());
        user.setRole(dto.getRole());
        return user;
    }
}
