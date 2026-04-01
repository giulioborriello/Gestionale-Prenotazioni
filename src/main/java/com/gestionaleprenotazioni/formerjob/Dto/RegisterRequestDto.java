package com.gestionaleprenotazioni.formerjob.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequestDto {

    private String name;
    private String surname;
    private String email;
    private String password;
    private String taxCode;
    private Date dateOfBirth;
}

