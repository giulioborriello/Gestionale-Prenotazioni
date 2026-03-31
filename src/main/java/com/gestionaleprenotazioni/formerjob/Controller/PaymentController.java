package com.gestionaleprenotazioni.formerjob.Controller;

import com.gestionaleprenotazioni.formerjob.Dto.PaymentDto;
import com.gestionaleprenotazioni.formerjob.Service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payment")
// 1. Fondamentale: Estendiamo l'AbstractController per avere le rotte base
public class PaymentController extends AbstractController<PaymentDto> {

    private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    /**
     * Lista di tutti i pagamenti effettuati.
     * Nota: Usiamo 'getAll()' che è il nome ereditato dall'AbstractService.
     */
    @GetMapping("/history")
    public ResponseEntity<Iterable<PaymentDto>> getAllPayments() {
        return ResponseEntity.ok(paymentService.getAll());
    }

    // Filtra pagamenti per metodo (es: /api/payment/method/paypal)
    @GetMapping("/method/{method}")
    public ResponseEntity<List<PaymentDto>> getByMethod(@PathVariable String method) {
        return ResponseEntity.ok(paymentService.findByMethod(method));
    }

    // Filtra per stato di verifica (checked)
    @GetMapping("/status/{checked}")
    public ResponseEntity<List<PaymentDto>> getByStatus(@PathVariable Boolean checked) {
        return ResponseEntity.ok(paymentService.findByChecked(checked));
    }

    // Recupera i pagamenti verificati di un utente
    @GetMapping("/user/{userId}/verified")
    public ResponseEntity<List<PaymentDto>> getVerifiedByUser(@PathVariable Integer userId) {
        return ResponseEntity.ok(paymentService.findAllVerifiedByUserId(userId));
    }
}