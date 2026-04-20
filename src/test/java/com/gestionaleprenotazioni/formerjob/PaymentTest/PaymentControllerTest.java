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

/**
 * Test di unità per {@link PaymentController}.
 * <p>
 * Questa classe testa il comportamento degli endpoint REST isolando il Controller
 * dal Service tramite l'utilizzo di Mockito. Si concentra sulla corretta gestione
 * delle risposte HTTP e sulla delega delle chiamate al servizio di business logic.
 * </p>
 */
@ExtendWith(MockitoExtension.class)
public class PaymentControllerTest {

    @Mock
    private PaymentService paymentService;

    @InjectMocks
    private PaymentController paymentController;

    /**
     * Metodo di utilità per creare un oggetto {@link PaymentDto} predefinito.
     * @return Un'istanza di PaymentDto popolata con dati di test.
     */
    private PaymentDto createPaymentDto() {
        PaymentDto dto = new PaymentDto();
        dto.setId(1);
        dto.setMethod(PaymentMethod.PAYPAL);
        dto.setTotalPrice(99.99);
        dto.setDate(LocalDateTime.of(2024, 1, 15, 10, 30));
        return dto;
    }
    /**
     * Verifica che l'endpoint per lo storico pagamenti restituisca correttamente
     * i dati ottenuti dal servizio.
     * <p>
     * Scenario: Il servizio restituisce una lista contenente un pagamento.<br>
     * Esito atteso: Risposta HTTP 200 OK e corpo del messaggio non nullo.
     * </p>
     */
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
    /**
     * Verifica il filtraggio dei pagamenti per metodo tramite l'endpoint dedicato.
     * <p>
     * Scenario: Richiesta di pagamenti effettuati con "PAYPAL".<br>
     * Esito atteso: La lista restituita contiene pagamenti con il metodo corretto.
     * </p>
     */
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
    /**
     * Verifica il recupero dei pagamenti associati a un utente specifico.
     * <p>
     * Scenario: Richiesta dei pagamenti per l'utente con ID 1.<br>
     * Esito atteso: Verifica che l'ID del pagamento restituito corrisponda a quello mockato.
     * </p>
     */
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