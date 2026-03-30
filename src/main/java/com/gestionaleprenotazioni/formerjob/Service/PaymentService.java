package com.gestionaleprenotazioni.formerjob.Service;

import com.gestionaleprenotazioni.formerjob.Dto.PaymentDto;
import com.gestionaleprenotazioni.formerjob.Mapper.PaymentMapper; // Assumendo che esista un PaymentMapper
import com.gestionaleprenotazioni.formerjob.Model.Payment;
import com.gestionaleprenotazioni.formerjob.Model.PaymentMethod;
import com.gestionaleprenotazioni.formerjob.Repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentService extends AbstractService<Payment, PaymentDto> {

    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;

    @Autowired
    public PaymentService(JpaRepository<Payment, Integer> repository, PaymentRepository paymentRepository, PaymentMapper paymentMapper) {
        super(repository, paymentMapper);
        this.paymentRepository = paymentRepository;
        this.paymentMapper = paymentMapper;
    }

    // JPQL: Pagamenti verificati per utente
    public List<PaymentDto> findAllVerifiedByUserId(Integer userId) {
        return paymentRepository.findAllVerifiedPaymentsByUserId(userId)
                .stream()
                .map(paymentMapper::toDTO)
                .toList();
    }

    // DERIVATO: Pagamenti filtrati per metodo (String -> Enum)
    public List<PaymentDto> findByMethod(String method) {
        PaymentMethod m = PaymentMethod.valueOf(method.toUpperCase());
        return paymentRepository.findByMethod(m)
                .stream()
                .map(paymentMapper::toDTO)
                .toList();
    }

    // DERIVATO: Pagamenti filtrati per stato (checked)
    public List<PaymentDto> findByChecked(Boolean checked) {
        return paymentRepository.findByChecked(checked)
                .stream()
                .map(paymentMapper::toDTO)
                .toList();
    }
}