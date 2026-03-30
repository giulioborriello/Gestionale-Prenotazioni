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

    // Riferimento all'utente (Molti carrelli possono appartenere a un utente nel tempo)
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * Relazione 1:1 inversa.
     * 'mappedBy = "cart"' indica che il campo che "comanda" la relazione (e che ha la FK)
     * si trova nella classe Payment ed è chiamato 'cart'.
     * CascadeType.ALL assicura che se cancelliamo il carrello, il pagamento associato venga gestito.
     */
    @OneToOne(mappedBy = "cart", cascade = CascadeType.ALL)
    private Payment payment;

    /**
     * Relazione 1:N con i ticket.
     * mappedBy = "cart" indica che nella tabella Ticket esiste una colonna cart_id.
     */
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
    @ToString.Exclude // Cruciale: evita cicli infiniti nel log se Ticket ha un riferimento a Cart
    private List<Ticket> tickets;
}