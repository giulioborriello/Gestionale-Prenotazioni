package com.gestionaleprenotazioni.formerjob.Controller;

import com.gestionaleprenotazioni.formerjob.Dto.PaymentDto;
import com.gestionaleprenotazioni.formerjob.Service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payment")

public class PaymentController extends AbstractController<PaymentDto> {

    private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }


    @GetMapping("/history")
    public ResponseEntity<Iterable<PaymentDto>> getAllPayments() {
        return ResponseEntity.ok(paymentService.getAll());
    }


    @GetMapping("/method/{method}")
    public ResponseEntity<List<PaymentDto>> getByMethod(@PathVariable String method) {
        return ResponseEntity.ok(paymentService.findByMethod(method));
    }


    @GetMapping("/status/{checked}")
    public ResponseEntity<List<PaymentDto>> getByStatus(@PathVariable Boolean checked) {
        return ResponseEntity.ok(paymentService.findByChecked(checked));
    }


    @GetMapping("/user/{userId}/verified")
    public ResponseEntity<List<PaymentDto>> getVerifiedByUser(@PathVariable Integer userId) {
        return ResponseEntity.ok(paymentService.findAllVerifiedByUserId(userId));
    }
}