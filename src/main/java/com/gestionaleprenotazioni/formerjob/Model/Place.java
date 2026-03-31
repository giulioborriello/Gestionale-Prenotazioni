package com.gestionaleprenotazioni.formerjob.Model;

import jakarta.persistence.*;

//classe Place rappresenta un posto per un evento
@Entity
public class Place {

    //id unico del posto
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //codice del posto es a1 b2 c3
    private String code;

    //stato del posto true = occupato, false = libero
    private boolean status;

    //tipo del posto sempre "STANDARD"
    private String type = "STANDARD";

    //evento a cui appartiene il posto (molti posti possono appartenere a un evento)
    @ManyToOne
    @JoinColumn(name = "event_id") //colonna che fa da foreign key verso Event
    private Event event;

    //costruttore vuoto
    public Place() {}

    //costruttore completo
    public Place(Integer id, String code, boolean status, Event event) {
        this.id = id;
        this.code = code;
        this.status = status;
        this.type = "STANDARD";
        this.event = event;
    }

    //gt st
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public boolean isStatus() { return status; }
    public void setStatus(boolean status) { this.status = status; }

    public String getType() { return type; }

    public Event getEvent() { return event; }
    public void setEvent(Event event) { this.event = event; }
}