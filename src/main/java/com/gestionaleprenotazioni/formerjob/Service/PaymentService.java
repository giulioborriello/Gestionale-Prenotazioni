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
    private final PaymentRepository paymentRepository;

    @Autowired
    public PaymentService(PaymentRepository paymentRepository, PaymentMapper paymentMapper) {

        super(paymentRepository, paymentMapper);
        this.paymentRepository = paymentRepository;
    }

    public List<PaymentDto> findByMethod(String method) {
        try {
            PaymentMethod m = PaymentMethod.valueOf(method.toUpperCase());
            return mapper.toDTOList(paymentRepository.findByMethod(m));
        } catch (IllegalArgumentException e) {

            return List.of();
        }
    }


    public List<PaymentDto> findByUserId(Integer userId) {
        return mapper.toDTOList(paymentRepository.findByUser_Id(userId));
    }
}