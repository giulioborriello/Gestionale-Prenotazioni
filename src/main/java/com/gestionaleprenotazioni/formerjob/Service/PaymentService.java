package com.gestionaleprenotazioni.formerjob.Service;

import com.gestionaleprenotazioni.formerjob.Dto.PaymentDto;
import com.gestionaleprenotazioni.formerjob.Mapper.PaymentMapper;
import com.gestionaleprenotazioni.formerjob.Model.Payment;
import com.gestionaleprenotazioni.formerjob.Model.PaymentMethod;
import com.gestionaleprenotazioni.formerjob.Repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
/**
 * Service per la gestione della logica di business relativa ai pagamenti.
 * <p>
 * Questa classe funge da intermediario tra il Controller e la Repository,
 * occupandosi della validazione dei dati e della conversione tra entità
 * {@link Payment} e oggetti {@link PaymentDto}.
 * </p>
 */
@Service
public class PaymentService extends AbstractService<Payment, PaymentDto> {
    private final PaymentRepository paymentRepository;


    /**
     * Costruttore per l'iniezione delle dipendenze.
     * @param paymentRepository La repository per l'accesso ai dati dei pagamenti.
     * @param paymentMapper Il componente per la mappatura dei dati.
     */
    @Autowired
    public PaymentService(PaymentRepository paymentRepository, PaymentMapper paymentMapper) {

        super(paymentRepository, paymentMapper);
        this.paymentRepository = paymentRepository;
    }

    /**
     * Ricerca i pagamenti filtrandoli per il metodo specificato (es. "PAYPAL", "CREDIT_CARD").
     * <p>
     * Il metodo converte la stringa in input nell'enumerazione {@link PaymentMethod}.
     * Se la stringa non corrisponde a nessun metodo valido, viene restituita una lista vuota.
     * </p>
     * @param method Il nome del metodo di pagamento da cercare (case-insensitive).
     * @return Una {@link List} di {@link PaymentDto} filtrata; lista vuota in caso di errore o nessun match.
     */
    public List<PaymentDto> findByMethod(String method) {
        try {
            PaymentMethod m = PaymentMethod.valueOf(method.toUpperCase());
            return mapper.toDTOList(paymentRepository.findByMethod(m));
        } catch (IllegalArgumentException e) {

            return List.of();
        }
    }

    /**
     * Recupera lo storico dei pagamenti effettuati da un determinato utente.
     * @param userId L'ID univoco dell'utente.
     * @return Una {@link List} di {@link PaymentDto} associati all'utente specificato.
     */
    public List<PaymentDto> findByUserId(Integer userId) {
        return mapper.toDTOList(paymentRepository.findByUser_Id(userId));
    }
}