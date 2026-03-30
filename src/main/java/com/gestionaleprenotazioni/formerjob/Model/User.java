package com.gestionaleprenotazioni.formerjob.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User
{
    @Id
    private Integer id;
    private String name;
    private String surname;
    private String email;
    private String password;
    private String taxCode;
    private String DateOfBirth;
    private String CreationDate;
    private boolean status;
}
