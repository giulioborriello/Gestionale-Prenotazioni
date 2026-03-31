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
@Table(name = "Place", schema = "formerjob")
public class Place {

    //id unic del posto
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

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

    // gt st
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