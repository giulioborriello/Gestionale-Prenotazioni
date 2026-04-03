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

    public PaymentController(PaymentService paymentService) {
        super(paymentService);
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


    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PaymentDto>> getByUserId(@PathVariable Integer userId) {
        return ResponseEntity.ok(paymentService.findByUserId(userId));
    }
}