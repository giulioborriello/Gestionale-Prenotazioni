package com.gestionaleprenotazioni.formerjob.Dto;

import com.gestionaleprenotazioni.formerjob.Model.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto
{
    private Integer id;

    private String name;

    private String surname;

    private String email;

    private String password;

    private String taxCode;

    private Date DateOfBirth;

    private Date CreationDate;

    private boolean status;

    private List<Integer> ticketIds;

    private Integer cartId;

    private Role role;

}
