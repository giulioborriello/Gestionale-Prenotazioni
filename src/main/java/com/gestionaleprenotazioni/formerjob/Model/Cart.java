package com.gestionaleprenotazioni.formerjob.Model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.ToString;
import java.util.List;

@Data
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

    // Riferimento all'utente proprietario del carrello
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Associazione opzionale con il pagamento (popolata a checkout completato)
    @OneToOne
    @JoinColumn(name = "payment_id")
    private Payment payment;

    // Lista dei ticket contenuti; cascade garantisce la persistenza dei ticket insieme al carrello
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
    @ToString.Exclude // Evita cicli infiniti durante la generazione della stringa toString
    private List<Ticket> tickets;
}