package com.gestionaleprenotazioni.formerjob.Service;

import com.gestionaleprenotazioni.formerjob.Dto.PaymentDto;
import com.gestionaleprenotazioni.formerjob.Mapper.PaymentMapper;
import com.gestionaleprenotazioni.formerjob.Model.Payment;
import com.gestionaleprenotazioni.formerjob.Model.PaymentMethod;
import com.gestionaleprenotazioni.formerjob.Repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentService extends AbstractService<Payment, PaymentDto> {

    // Usiamo direttamente il nostro repository specifico
    private final PaymentRepository paymentRepository;

    @Autowired
    public PaymentService(PaymentRepository paymentRepository, PaymentMapper paymentMapper) {

        super(paymentRepository, paymentMapper);
        this.paymentRepository = paymentRepository;
    }

    // 1. JPQL: Pagamenti verificati per utente
    public List<PaymentDto> findAllVerifiedByUserId(Integer userId) {

        return mapper.toDTOList(paymentRepository.findAllVerifiedPaymentsByUserId(userId));
    }

    // 2. DERIVATO: Pagamenti filtrati per metodo (String -> Enum)
    public List<PaymentDto> findByMethod(String method) {
        try {
            PaymentMethod m = PaymentMethod.valueOf(method.toUpperCase());
            return mapper.toDTOList(paymentRepository.findByMethod(m));
        } catch (IllegalArgumentException e) {
            // Se il metodo non esiste, restituiamo una lista vuota invece di crashare
            return List.of();
        }
    }

    // 3. DERIVATO: Pagamenti filtrati per stato (checked)
    public List<PaymentDto> findByChecked(Boolean checked) {

        return mapper.toDTOList(paymentRepository.findByChecked(checked));
    }
}