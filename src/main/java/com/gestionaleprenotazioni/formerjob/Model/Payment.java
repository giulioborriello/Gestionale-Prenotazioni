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

    // Salviamo il nome della costante Enum (es. "CREDIT_CARD") invece dell'indice numerico
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentMethod method;

    // Flag per verificare se il pagamento è andato a buon fine
    @Column(nullable = false)
    private Boolean checked;

    // Timestamp della transazione
    @Column(nullable = false)
    private LocalDateTime date;


    @OneToOne
    @JoinColumn(name = "cart_id", referencedColumnName = "id", nullable = false)
    private Cart cart;
}