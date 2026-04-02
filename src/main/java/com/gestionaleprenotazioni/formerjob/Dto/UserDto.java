package com.gestionaleprenotazioni.formerjob.Dto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto
{
    @Id
    private Integer id;

    private String name;

    private String surname;

    private String email;

    private String password;

    private String taxCode;

    private Date DateOfBirth;

    private Date CreationDate;

    private boolean status;

}
