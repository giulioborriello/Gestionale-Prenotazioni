package com.gestionaleprenotazioni.formerjob.Model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cart", schema = "formerjob")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "total_price", nullable = false)
    private Double totalPrice;


    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;


    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
    @ToString.Exclude // evita cicli infiniti nel log
    private List<Payment> payments;


    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
    @ToString.Exclude // evita cicli infiniti nel log
    private List<Ticket> tickets;
}