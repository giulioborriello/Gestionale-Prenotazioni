package com.gestionaleprenotazioni.formerjob.PaymentTest;

import com.gestionaleprenotazioni.formerjob.Dto.PaymentDto;
import com.gestionaleprenotazioni.formerjob.Mapper.PaymentMapper;
import com.gestionaleprenotazioni.formerjob.Model.Payment;
import com.gestionaleprenotazioni.formerjob.Model.PaymentMethod;
import com.gestionaleprenotazioni.formerjob.Repository.PaymentRepository;
import com.gestionaleprenotazioni.formerjob.Service.PaymentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Test di unità per {@link PaymentService}.
 * <p>
 * Questa classe verifica la logica di business dello strato Service, assicurando
 * la corretta interazione tra la Repository e il Mapper. Tutti i test utilizzano
 * oggetti Mock per isolare la logica del servizio dalle dipendenze esterne.
 * </p>
 */
@ExtendWith(MockitoExtension.class)
public class PaymentServiceTest {

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private PaymentMapper paymentMapper;

    @InjectMocks
    private PaymentService paymentService;

    /**
     * Crea un'entità {@link Payment} di test.
     * @return Istanza di Payment popolata per il testing.
     */
    private Payment createPayment() {
        Payment payment = new Payment();
        payment.setId(1);
        payment.setMethod(PaymentMethod.PAYPAL);
        payment.setDate(LocalDateTime.of(2024, 1, 15, 10, 30));
        return payment;
    }

    /**
     * Crea un {@link PaymentDto} di test.
     * @return Istanza di PaymentDto popolata per il testing.
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
     * Verifica il recupero di tutti i pagamenti.
     * <p>Assicura che la chiamata al repository venga mappata correttamente in una lista di DTO.</p>
     */
    @Test
    void testGetAll() {
        Payment payment = createPayment();
        PaymentDto dto = createPaymentDto();

        // simuliamo repository.findAll() e mapper.toDTOList()
        when(paymentRepository.findAll()).thenReturn(List.of(payment));
        when(paymentMapper.toDTOList(List.of(payment))).thenReturn(List.of(dto));

        Iterable<PaymentDto> result = paymentService.getAll();

        assertThat(result).isNotNull();
        verify(paymentRepository).findAll();
        verify(paymentMapper).toDTOList(List.of(payment));
    }
    /**
     * Verifica la lettura di un singolo pagamento tramite ID.
     */
    @Test
    void testRead() {
        Payment payment = createPayment();
        PaymentDto dto = createPaymentDto();

        // simuliamo repository.findById() e mapper.toDTO()
        when(paymentRepository.findById(1)).thenReturn(Optional.of(payment));
        when(paymentMapper.toDTO(payment)).thenReturn(dto);

        PaymentDto result = paymentService.read(1);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1);
        verify(paymentRepository).findById(1);
        verify(paymentMapper).toDTO(payment);
    }
    /**
     * Verifica l'inserimento di un nuovo pagamento.
     * <p>Testa il flusso completo: DTO -> Entity -> Save -> DTO.</p>
     */
    @Test
    void testInsert() {
        Payment payment = createPayment();
        PaymentDto dto = createPaymentDto();

        // simuliamo mapper.toEntity(), repository.save() e mapper.toDTO()
        when(paymentMapper.toEntity(dto)).thenReturn(payment);
        when(paymentRepository.save(payment)).thenReturn(payment);
        when(paymentMapper.toDTO(payment)).thenReturn(dto);

        PaymentDto result = paymentService.insert(dto);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1);
        verify(paymentMapper).toEntity(dto);
        verify(paymentRepository).save(payment);
        verify(paymentMapper).toDTO(payment);
    }
    /**
     * Verifica l'aggiornamento di un pagamento esistente.
     */
    @Test
    void testUpdate() {
        Payment payment = createPayment();
        PaymentDto dto = createPaymentDto();

        // update usa save() esattamente come insert()
        when(paymentMapper.toEntity(dto)).thenReturn(payment);
        when(paymentRepository.save(payment)).thenReturn(payment);
        when(paymentMapper.toDTO(payment)).thenReturn(dto);

        PaymentDto result = paymentService.update(dto);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1);
        verify(paymentMapper).toEntity(dto);
        verify(paymentRepository).save(payment);
        verify(paymentMapper).toDTO(payment);
    }
    /**
     * Verifica la cancellazione logica o fisica di un pagamento tramite ID.
     */
    @Test
    void testDelete() {
        // delete non ritorna nulla, verifichiamo solo che venga chiamato
        paymentService.delete(1);
        verify(paymentRepository).deleteById(1);
    }
    /**
     * Verifica la ricerca filtrata per metodo di pagamento valido.
     */
    @Test
    void testFindByMethod() {
        Payment payment = createPayment();
        PaymentDto dto = createPaymentDto();

        // metodo derivato — converte la stringa in enum internamente
        when(paymentRepository.findByMethod(PaymentMethod.PAYPAL)).thenReturn(List.of(payment));
        when(paymentMapper.toDTOList(List.of(payment))).thenReturn(List.of(dto));

        List<PaymentDto> result = paymentService.findByMethod("PAYPAL");

        assertThat(result).isNotNull();
        assertThat(result.get(0).getMethod()).isEqualTo(PaymentMethod.PAYPAL);
        verify(paymentRepository).findByMethod(PaymentMethod.PAYPAL);
        verify(paymentMapper).toDTOList(List.of(payment));
    }
    /**
     * Verifica la robustezza del sistema in caso di metodo di pagamento non valido.
     * <p>Assicura che il servizio gestisca l'errore senza eccezioni, restituendo una lista vuota.</p>
     */
    @Test
    void testFindByMethodInvalid() {
        // caso in cui il metodo passato non esiste nell'enum
        // il service catcha l'eccezione e ritorna List.of()
        List<PaymentDto> result = paymentService.findByMethod("METODO_INESISTENTE");

        assertThat(result).isNotNull();
        // nessuna chiamata al repository perché l'enum non esiste
        verify(paymentRepository, org.mockito.Mockito.never())
                .findByMethod(org.mockito.ArgumentMatchers.any());
    }
    /**
     * Verifica la ricerca di tutti i pagamenti effettuati da un utente specifico.
     */
    @Test
    void testFindByUserId() {
        Payment payment = createPayment();
        PaymentDto dto = createPaymentDto();

        // metodo derivato per ricercare pagamenti per ID utente
        when(paymentRepository.findByUser_Id(1)).thenReturn(List.of(payment));
        when(paymentMapper.toDTOList(List.of(payment))).thenReturn(List.of(dto));

        List<PaymentDto> result = paymentService.findByUserId(1);

        assertThat(result).isNotNull();
        assertThat(result.get(0).getId()).isEqualTo(1);
        verify(paymentRepository).findByUser_Id(1);
        verify(paymentMapper).toDTOList(List.of(payment));
    }
}