package com.gestionaleprenotazioni.formerjob.PaymentTest;

import com.gestionaleprenotazioni.formerjob.Controller.PaymentController;
import com.gestionaleprenotazioni.formerjob.Dto.PaymentDto;
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
        dto.setOrderId(10);
        dto.setAmount(99.99);
        dto.setStatus("COMPLETED");
        dto.setMethod("PAYPAL");
        dto.setTransactionId("TXN-001");
        dto.setAttemptedAt(LocalDateTime.of(2024, 1, 15, 10, 30));
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
        assertThat(result.getBody().get(0).getMethod()).isEqualTo("PAYPAL");
        verify(paymentService).findByMethod("PAYPAL");
    }

    @Test
    void testGetByStatus() {
        PaymentDto dto = createPaymentDto();
        // getByStatus() chiama paymentService.findByChecked() internamente
        when(paymentService.findByChecked(true)).thenReturn(List.of(dto));

        ResponseEntity<List<PaymentDto>> result = paymentController.getByStatus(true);

        assertThat(result).isNotNull();
        assertThat(result.getBody()).isNotNull();
        verify(paymentService).findByChecked(true);
    }

    @Test
    void testGetVerifiedByUser() {
        PaymentDto dto = createPaymentDto();
        // getVerifiedByUser() chiama paymentService.findAllVerifiedByUserId() internamente
        when(paymentService.findAllVerifiedByUserId(1)).thenReturn(List.of(dto));

        ResponseEntity<List<PaymentDto>> result = paymentController.getVerifiedByUser(1);

        assertThat(result).isNotNull();
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().get(0).getId()).isEqualTo(1);
        verify(paymentService).findAllVerifiedByUserId(1);
    }
}