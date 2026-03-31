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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // identificativo dell'evento

    @Column(length=100,nullable=false)
    private String name; // nome dell'evento

    @Column(length=300,nullable=false)
    private String description; // descrizione dell'evento

    @Column(length=50,nullable=false)
    private String location; // luogo dell'evento

    @Column(nullable=false)
    private Date date; // data dell'evento

    @Enumerated(EnumType.STRING)
    private Type type; // tipologia di evento

    @OneToOne
    @JoinColumn(name = "place_id", referencedColumnName = "id", nullable = false)
    private Place place;

    @OneToMany(mappedBy = "event")
    private List<Ticket> tickets;


}
