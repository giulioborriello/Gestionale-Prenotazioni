package com.gestionaleprenotazioni.formerjob.Service;

import com.gestionaleprenotazioni.formerjob.Dto.TicketDto;
import com.gestionaleprenotazioni.formerjob.Mapper.TicketMapper;
import com.gestionaleprenotazioni.formerjob.Model.Event;
import com.gestionaleprenotazioni.formerjob.Model.Payment;
import com.gestionaleprenotazioni.formerjob.Model.Ticket;
import com.gestionaleprenotazioni.formerjob.Model.User;
import com.gestionaleprenotazioni.formerjob.Repository.EventRepository;
import com.gestionaleprenotazioni.formerjob.Repository.PaymentRepository;
import com.gestionaleprenotazioni.formerjob.Repository.TicketRepository;
import com.gestionaleprenotazioni.formerjob.Repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Service layer per la gestione dei ticket.
 *
 * <p>Questa classe estende {@link AbstractService} per riusare operazioni CRUD base
 * e aggiunge logica specifica per:</p>
 * <ul>
 * <li>validazione input in insert/update</li>
 * <li>risoluzione relazioni {@link User}, {@link Event} e {@link Payment}</li>
 * <li>ricerche filtrate su ticket</li>
 * </ul>
 */
@Service
public class TicketService extends AbstractService<Ticket, TicketDto>{
    private final TicketMapper ticketMapper;
    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final PaymentRepository paymentRepository;

    /**
     * Costruttore con dependency injection dei componenti necessari.
     *
     * @param ticketMapper mapper entity/DTO per i ticket
     * @param ticketRepository repository JPA dei ticket
     * @param userRepository repository JPA degli utenti
     * @param eventRepository repository JPA degli eventi
     * @param paymentRepository repository JPA dei pagamenti
     */
    public TicketService(TicketMapper ticketMapper,
                         TicketRepository ticketRepository,
                         UserRepository userRepository,
                         EventRepository eventRepository,
                         PaymentRepository paymentRepository) {
        super(ticketRepository, ticketMapper);
        this.ticketMapper = ticketMapper;
        this.ticketRepository = ticketRepository;
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
        this.paymentRepository = paymentRepository;
    }

    /**
     * Inserisce un nuovo ticket a partire dal DTO in ingresso.
     */
    @Override
    @Transactional
    public TicketDto insert(TicketDto dto) {
        Ticket ticket = buildTicketFromDto(dto);
        incrementSelledTickets(ticket.getEvent());
        return ticketMapper.toDTO(ticketRepository.save(ticket));
    }

    /**
     * Aggiorna un ticket esistente.
     */
    @Override
    public TicketDto update(TicketDto dto) {
        if (dto == null || dto.getTicketId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ticketId is required for update");
        }

        if (!ticketRepository.existsById(dto.getTicketId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ticket not found with id: " + dto.getTicketId());
        }

        Ticket ticket = buildTicketFromDto(dto);
        return ticketMapper.toDTO(ticketRepository.save(ticket));
    }

    /**
     * Costruisce una entity {@link Ticket} a partire da {@link TicketDto},
     * risolvendo le relazioni con evento, utente e pagamento.
     *
     * @param dto payload sorgente
     * @return entity ticket pronta per essere salvata
     */
    private Ticket buildTicketFromDto(TicketDto dto) {
        if (dto == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ticket payload is required");
        }

        if (dto.getEventId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "eventId is required");
        }

        Ticket ticket = ticketMapper.toEntity(dto);

        // Risoluzione relazioni
        ticket.setEvent(resolveEvent(dto.getEventId()));

        if (dto.getUserId() != null) {
            ticket.setUser(resolveUser(dto.getUserId()));
        }

        if (dto.getPaymentId() != null) {
            ticket.setPayment(resolvePayment(dto.getPaymentId()));
        }

        return ticket;
    }

    private User resolveUser(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with id: " + id));
    }

    private Event resolveEvent(Integer id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found with id: " + id));
    }

    private Payment resolvePayment(Integer id) {
        return paymentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Payment not found with id: " + id));
    }

    private void incrementSelledTickets(Event event) {
        if (event.getSelledTickets() == null) {
            event.setSelledTickets(0);
        }
        event.setSelledTickets(event.getSelledTickets() + 1);
    }

    // --- Metodi di ricerca rimasti invariati ---
    public List<TicketDto> findTicketByNameAndSurname(String name, String surname) {
        return ticketMapper.toDTOList(ticketRepository.findTicketByNameAndSurname(name, surname));
    }

    public List<TicketDto> findTicketByCreationDate(LocalDateTime creationDate) {
        return ticketMapper.toDTOList(ticketRepository.findTicketByCreationDate(creationDate));
    }

    public List<TicketDto> findTicketByPriceGreaterThanEqual(Double price) {
        return ticketMapper.toDTOList(ticketRepository.findTicketByPriceGreaterThanEqual(price));
    }

    public List<TicketDto> findTicketByPriceLessThanEqual(Double priceIsLessThan) {
        return ticketMapper.toDTOList(ticketRepository.findTicketByPriceLessThanEqual(priceIsLessThan));
    }

    public List<TicketDto> findTicketByPriceRange(Double initialPrice, Double endPrice) {
        return ticketMapper.toDTOList(ticketRepository.findTicketByPriceRange(initialPrice, endPrice));
    }

    public List<TicketDto> findTicketByDateRange(LocalDateTime startTime, LocalDateTime endTime) {
        return ticketMapper.toDTOList(ticketRepository.findTicketByDateRange(startTime, endTime));
    }
    public List<TicketDto> findTicketByUserId(Integer user_id)
    {
        return ticketMapper.toDTOList((ticketRepository.findTicketByUserId(user_id)));
    }
}