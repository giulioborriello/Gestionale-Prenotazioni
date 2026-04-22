package com.gestionaleprenotazioni.formerjob.CheckoutTest;

import com.gestionaleprenotazioni.formerjob.Dto.PaymentDto;
import com.gestionaleprenotazioni.formerjob.Model.Payment;
import com.gestionaleprenotazioni.formerjob.Model.PaymentMethod;
import com.gestionaleprenotazioni.formerjob.Repository.PaymentRepository;
import com.gestionaleprenotazioni.formerjob.Repository.TicketRepository;
import com.gestionaleprenotazioni.formerjob.Service.CheckoutService;
import com.gestionaleprenotazioni.formerjob.Service.EmailService;
import com.gestionaleprenotazioni.formerjob.Service.PdfService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CheckoutServiceTest {

    @Autowired
    private CheckoutService checkoutService;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @MockitoBean
    private EmailService emailService;

    @MockitoBean
    private PdfService pdfService;

    @BeforeEach
    void setup() {
        // Configuro i mock per evitare NullPointerException durante l'esecuzione del service
        Mockito.when(pdfService.generaPdfOrdine(Mockito.anyString(), Mockito.anyString(), Mockito.anyDouble(), Mockito.anyString(), Mockito.anyString(), Mockito.any()))
                .thenReturn(new byte[]{1, 2, 3});

        Mockito.when(emailService.buildPurchaseConfirmationSubject(Mockito.anyString()))
                .thenReturn("Oggetto Prova");

        Mockito.when(emailService.buildPurchaseConfirmationBody(Mockito.any(), Mockito.any()))
                .thenReturn("Corpo Prova");
    }

    @Test
    void testCompletePurchase_Success() {
        // 1. Arrange
        PaymentDto dto = new PaymentDto();
        dto.setUserId(1); // Mario Merola (da data.sql)
        dto.setEventId(1); // Coldplay (da data.sql)
        dto.setTotalPrice(250.00);
        dto.setMethod(PaymentMethod.CREDIT_CARD);
        dto.setDate(LocalDateTime.now());

        long initialPaymentCount = paymentRepository.count();
        long initialTicketCount = ticketRepository.count();

        // 2. Act
        assertDoesNotThrow(() -> checkoutService.completePurchase(dto), "Il metodo completePurchase non dovrebbe lanciare eccezioni");

        // 3. Assert
        assertEquals(initialPaymentCount + 1, paymentRepository.count(), "Dovrebbe esserci un nuovo pagamento nel DB");
        assertEquals(initialTicketCount + 1, ticketRepository.count(), "Dovrebbe esserci un nuovo ticket nel DB");

        List<Payment> payments = paymentRepository.findAll();
        Payment lastPayment = payments.get(payments.size() - 1);
        assertEquals(250.00, lastPayment.getTotalPrice());
    }

}
