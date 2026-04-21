package com.gestionaleprenotazioni.formerjob.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;
//Getter è un annotazione che autoimplementa i metodi getter
@Getter
//Setter è un annotazione che autoimplementa i setter
@Setter
//AllArgaConstrucor è un annotazione che autoimplementa un costruttore con tutti i campi
@AllArgsConstructor
//NoArgsConstructor è un annotazione che autoimplementa un costruttore senza campi
@NoArgsConstructor
// Entity è un annotazione che dichira che quella che si vede è un entità
@Entity
//Table è un annotazione che dichiara la tabella di DB dell' entità
@Table(name = "User", schema = "formerjob")
public class User
{
    //Id è l' annotazione per la chiave primaria
    @Id
    //GeneratedValue serve per l'aggiunta di nuovi User(id) e li genera automaticamente
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String surname;

    @Column(unique = true)
    private String email;

    private String password;

    private Date dateOfBirth;

    private Role role;

    //RELAZIONE 1-N (MappedBy serve per indicare che user è il proprietario della relazione- non creare joinColumn qui)
    @OneToMany(mappedBy = "user")

    private List<Ticket> tickets;
    //RELAZIONE 1-N (MappedBy serve per indicare che user è il proprietario della relazione)
    @OneToMany(mappedBy = "user")
    private List<Payment> payments;


}
