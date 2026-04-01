package com.gestionaleprenotazioni.formerjob.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//classe place rappresenta un posto per n evento
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Place", schema = "formerjob")
public class Place {

    //id unic del posto
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //nome del posto
    private String nome;

    //codice del posto es a1 b2 c3
    private String code;

    //stato del posto true = occupato, false = libero
    private boolean status;

    //tipo del posto sempre "STANDARD"
    private String type = "STANDARD";

    //evento a cui appartiene il posto
    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;


}