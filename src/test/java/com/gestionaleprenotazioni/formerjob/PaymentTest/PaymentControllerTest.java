package com.gestionaleprenotazioni.formerjob.PaymentTest;

import com.gestionaleprenotazioni.formerjob.Controller.PaymentController;
import com.gestionaleprenotazioni.formerjob.Dto.PaymentDto;
import com.gestionaleprenotazioni.formerjob.Model.PaymentMethod;
import com.gestionaleprenotazioni.formerjob.Service.PaymentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PaymentControllerTest {

    @Mock
    private PaymentService paymentService;

    @InjectMocks
    private PaymentController paymentController;

    // metodo di supporto per creare un PaymentDto di test
    private PaymentDto createPaymentDto() {
        PaymentDto dto = new PaymentDto();
        dto.setId(1);
        dto.setMethod(PaymentMethod.PAYPAL);
        dto.setTotalPrice(99.99);
        dto.setDate(LocalDateTime.of(2024, 1, 15, 10, 30));
        return dto;
    }

    @Test
    void testGetAllPayments() {
        PaymentDto dto = createPaymentDto();
        // getAllPayments() chiama paymentService.getAll() internamente
        when(paymentService.getAll()).thenReturn(List.of(dto));

        ResponseEntity<Iterable<PaymentDto>> result = paymentController.getAllPayments();

        assertThat(result).isNotNull();
        assertThat(result.getBody()).isNotNull();
        verify(paymentService).getAll();
    }

    @Test
    void testGetByMethod() {
        PaymentDto dto = createPaymentDto();
        // getByMethod() chiama paymentService.findByMethod() internamente
        when(paymentService.findByMethod("PAYPAL")).thenReturn(List.of(dto));

        ResponseEntity<List<PaymentDto>> result = paymentController.getByMethod("PAYPAL");

        assertThat(result).isNotNull();
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().get(0).getMethod()).isEqualTo(PaymentMethod.PAYPAL);
        verify(paymentService).findByMethod("PAYPAL");
    }

    @Test
    void testGetByUserId() {
        PaymentDto dto = createPaymentDto();
        // getByUserId() chiama paymentService.findByUserId() internamente
        when(paymentService.findByUserId(1)).thenReturn(List.of(dto));

        ResponseEntity<List<PaymentDto>> result = paymentController.getByUserId(1);

        assertThat(result).isNotNull();
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().get(0).getId()).isEqualTo(1);
        verify(paymentService).findByUserId(1);
    }
}