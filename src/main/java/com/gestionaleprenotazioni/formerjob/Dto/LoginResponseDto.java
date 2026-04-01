package com.gestionaleprenotazioni.formerjob.Dto;

import com.gestionaleprenotazioni.formerjob.Model.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDto {

    private Integer id;
    private String name;
    private String surname;
    private String email;
    private Role role;
    private boolean status;
}

