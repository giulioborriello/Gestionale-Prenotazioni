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
     * Relazione 1:N con i pagamenti.
     * Un carrello può avere più tentativi di pagamento.
     * 'mappedBy = "cart"' indica che la FK cart_id si trova nella tabella Payment.
     */
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
    @ToString.Exclude // evita cicli infiniti nel log
    private List<Payment> payments;

    /**
     * Relazione 1:N con i ticket.
     * mappedBy = "cart" indica che nella tabella Ticket esiste una colonna cart_id.
     */
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
    @ToString.Exclude // evita cicli infiniti nel log
    private List<Ticket> tickets;
}