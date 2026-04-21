package com.gestionaleprenotazioni.formerjob.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Event", schema = "formerjob")
public class Event {
//Questa classe rappresenta un evento reale nel sistema: nome, descrizione, luogo, data, tipo, prezzo e biglietti.
// È collegata ai biglietti venduti tramite relazione uno-a-molti.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // identificativo dell'evento

    @Column(length=100,nullable=false)
    private String name; // nome dell'evento

    @Column(length=300,nullable=false)
    private String description; // descrizione dell'evento

    @Column(length=50,nullable=false)
    private String location; // luogo dell'evento

    @Column(length=100,nullable=false)
    private String imagePath; // immagine dell'evento

    @Column(nullable=false)
    private Date date; // data dell'evento

    @Column(nullable=false)
    private Integer maxTickets = 250;

    @Column(nullable=false)
    private Integer selledTickets = 0;

    @Enumerated(EnumType.STRING)
    private Type type; // tipologia di evento

    @Column(nullable=false)
    private Double ticketPrice;

    @OneToMany(mappedBy = "event")
    private List<Ticket> tickets;


}
