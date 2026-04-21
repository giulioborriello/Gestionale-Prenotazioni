package com.gestionaleprenotazioni.formerjob.Controller;

import com.gestionaleprenotazioni.formerjob.Dto.PaymentDto;
import com.gestionaleprenotazioni.formerjob.Service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller per la gestione degli endpoint dei pagamenti.
 * <p>
 * Espone le API per consultare lo storico dei pagamenti, filtrare per metodo
 * o per utente. Risponde all'indirizzo base {@code /api/payment}.
 * </p>
 */
@RestController
@RequestMapping("/api/payment")
@CrossOrigin(origins = "http://localhost:4200")
@Tag(name = "Pagamenti", description = "Area dedicata alla consultazione e gestione dei flussi monetari")
public class PaymentController extends AbstractController<PaymentDto> {

    private final PaymentService paymentService;

    /**
     * Costruttore per l'iniezione del servizio pagamenti.
     * @param paymentService Il servizio di business logic.
     */
    public PaymentController(PaymentService paymentService) {
        super(paymentService);
        this.paymentService = paymentService;
    }

    /**
     * Recupera lo storico completo di tutti i pagamenti registrati.
     * @return {@link ResponseEntity} contenente la lista di tutti i {@link PaymentDto}
     * con stato HTTP 200 (OK).
     */
    @Operation(summary = "Ottieni storico pagamenti", description = "Recupera la lista completa di tutti i pagamenti presenti nel database")
    @GetMapping("/history")
    public ResponseEntity<Iterable<PaymentDto>> getAllPayments() {
        return ResponseEntity.ok(paymentService.getAll());
    }

    /**
     * Filtra i pagamenti in base al metodo specificato nell'URL.
     * @param method Il metodo di pagamento (es. "paypal", "credit_card").
     * @return {@link ResponseEntity} con la lista dei pagamenti corrispondenti;
     * una lista vuota se il metodo non è valido.
     */
    @Operation(summary = "Filtra per metodo", description = "Restituisce i pagamenti filtrati in base alla tipologia selezionata (es. PAYPAL)")
    @GetMapping("/method/{method}")
    public ResponseEntity<List<PaymentDto>> getByMethod(@PathVariable String method) {
        return ResponseEntity.ok(paymentService.findByMethod(method));
    }

    /**
     * Recupera tutti i pagamenti effettuati da uno specifico utente.
     * @param userId L'ID dell'utente passato come variabile di percorso (Path Variable).
     * @return {@link ResponseEntity} contenente la lista dei pagamenti dell'utente.
     */
    @Operation(summary = "Cerca per ID Utente", description = "Recupera lo storico dei pagamenti associati a un singolo identificativo utente")
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PaymentDto>> getByUserId(@PathVariable Integer userId) {
        return ResponseEntity.ok(paymentService.findByUserId(userId));
    }

}