package com.gestionaleprenotazioni.formerjob.Model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "payment", schema = "formerjob")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // Specifichiamo che l'enum deve essere salvata come stringa nel DB (es: "PAYPAL")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentMethod method;

    // Indica se il pagamento è stato verificato o andato a buon fine
    @Column(nullable = false)
    private Boolean checked;

    // La data e l'ora in cui è avvenuta la transazione
    @Column(nullable = false)
    private LocalDateTime date;

    /**
     * Relazione 1:1 con il carrello.
     * Il cart_id sarà la chiave esterna in questa tabella.
     */
    @OneToOne
    @JoinColumn(name = "cart_id", referencedColumnName = "id")
    private Cart cart;
}
