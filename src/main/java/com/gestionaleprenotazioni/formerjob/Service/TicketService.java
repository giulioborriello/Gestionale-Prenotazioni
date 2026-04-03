package com.gestionaleprenotazioni.formerjob.Service;

import com.gestionaleprenotazioni.formerjob.Dto.TicketDto;
import com.gestionaleprenotazioni.formerjob.Mapper.TicketMapper;
import com.gestionaleprenotazioni.formerjob.Model.Event;
import com.gestionaleprenotazioni.formerjob.Model.Ticket;
import com.gestionaleprenotazioni.formerjob.Model.User;
import com.gestionaleprenotazioni.formerjob.Repository.EventRepository;
import com.gestionaleprenotazioni.formerjob.Repository.TicketRepository;
import com.gestionaleprenotazioni.formerjob.Repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Service layer per la gestione dei ticket.
 *
 * <p>Questa classe estende {@link AbstractService} per riusare operazioni CRUD base
 * e aggiunge logica specifica per:</p>
 * <ul>
 *     <li>validazione input in insert/update</li>
 *     <li>risoluzione relazioni {@link User} e {@link Event}</li>
 *     <li>ricerche filtrate su ticket</li>
 * </ul>
 */
@Service
public class TicketService extends AbstractService<Ticket, TicketDto>{
    private final TicketMapper ticketMapper;
    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    /**
     * Costruttore con dependency injection dei componenti necessari.
     *
     * @param ticketMapper mapper entity/DTO per i ticket
     * @param ticketRepository repository JPA dei ticket
     * @param userRepository repository JPA degli utenti
     * @param eventRepository repository JPA degli eventi
     */
    public TicketService(TicketMapper ticketMapper,
                         TicketRepository ticketRepository,
                         UserRepository userRepository,
                         EventRepository eventRepository) {
        super(ticketRepository, ticketMapper);
        this.ticketMapper = ticketMapper;
        this.ticketRepository = ticketRepository;
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
    }

    /**
     * Inserisce un nuovo ticket a partire dal DTO in ingresso.
     *
     * <p>Valida e costruisce l'entita con {@link #buildTicketFromDto(TicketDto)},
     * quindi la persiste e restituisce il DTO salvato.</p>
     *
     * @param dto payload del ticket da inserire
     * @return ticket salvato in formato DTO
     * @throws ResponseStatusException BAD_REQUEST se il payload non e valido
     * @throws ResponseStatusException NOT_FOUND se utente/evento referenziati non esistono
     */
    @Override
    public TicketDto insert(TicketDto dto) {
        Ticket ticket = buildTicketFromDto(dto);
        return ticketMapper.toDTO(ticketRepository.save(ticket));
    }

    /**
     * Aggiorna un ticket esistente.
     *
     * <p>Richiede un {@code ticketId} valorizzato nel DTO e verifica che il ticket
     * esista prima del salvataggio.</p>
     *
     * @param dto payload del ticket da aggiornare
     * @return ticket aggiornato in formato DTO
     * @throws ResponseStatusException BAD_REQUEST se DTO o ticketId sono null
     * @throws ResponseStatusException NOT_FOUND se il ticket non esiste
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
     * risolvendo le relazioni con evento e utente.
     *
     * <p>{@code eventId} e obbligatorio, mentre {@code userId} e opzionale.</p>
     *
     * @param dto payload sorgente
     * @return entity ticket pronta per essere salvata
     * @throws ResponseStatusException BAD_REQUEST se DTO o eventId sono null
     * @throws ResponseStatusException NOT_FOUND se utente/evento non esistono
     */
    private Ticket buildTicketFromDto(TicketDto dto) {
        if (dto == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ticket payload is required");
        }

        if (dto.getEventId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "eventId is required");
        }

        Ticket ticket = ticketMapper.toEntity(dto);
        ticket.setEvent(resolveEvent(dto.getEventId()));
        if (dto.getUserId() != null) {
            ticket.setUser(resolveUser(dto.getUserId()));
        }

        return ticket;
    }

    /**
     * Recupera un utente per ID.
     *
     * @param id identificativo utente
     * @return utente trovato
     * @throws ResponseStatusException NOT_FOUND se l'utente non esiste
     */
    private User resolveUser(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with id: " + id));
    }

    /**
     * Recupera un evento per ID.
     *
     * @param id identificativo evento
     * @return evento trovato
     * @throws ResponseStatusException NOT_FOUND se l'evento non esiste
     */
    private Event resolveEvent(Integer id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found with id: " + id));
    }

    /**
     * Cerca ticket per nome e cognome.
     *
     * @param name nome intestatario
     * @param surname cognome intestatario
     * @return lista di ticket DTO corrispondenti
     */
    public List<TicketDto> findTicketByNameAndSurname(String name, String surname) {
        return ticketMapper.toDTOList(ticketRepository.findTicketByNameAndSurname(name, surname));
    }

    /**
     * Cerca ticket per data/ora di creazione esatta.
     *
     * @param creationDate data e ora di creazione
     * @return lista di ticket DTO corrispondenti
     */
    public List<TicketDto> findTicketByCreationDate(LocalDateTime creationDate) {
        return ticketMapper.toDTOList(ticketRepository.findTicketByCreationDate(creationDate));
    }

    /**
     * Cerca ticket con prezzo maggiore o uguale alla soglia.
     *
     * @param price soglia minima inclusa
     * @return lista di ticket DTO corrispondenti
     */
    public List<TicketDto> findTicketByPriceGreaterThanEqual(Double price) {
        return ticketMapper.toDTOList(ticketRepository.findTicketByPriceGreaterThanEqual(price));
    }

    /**
     * Cerca ticket con prezzo minore o uguale alla soglia.
     *
     * @param priceIsLessThan soglia massima inclusa
     * @return lista di ticket DTO corrispondenti
     */
    public List<TicketDto> findTicketByPriceLessThanEqual(Double priceIsLessThan) {
        return ticketMapper.toDTOList(ticketRepository.findTicketByPriceLessThanEqual(priceIsLessThan));
    }

    /**
     * Cerca ticket in un range di prezzo (estremi inclusi).
     *
     * @param initialPrice prezzo iniziale del range
     * @param endPrice prezzo finale del range
     * @return lista di ticket DTO corrispondenti
     */
    public List<TicketDto> findTicketByPriceRange(Double initialPrice, Double endPrice) {
        return ticketMapper.toDTOList(ticketRepository.findTicketByPriceRange(initialPrice, endPrice));
    }

    /**
     * Cerca ticket in un range temporale di creazione (estremi inclusi).
     *
     * @param startTime inizio intervallo temporale
     * @param endTime fine intervallo temporale
     * @return lista di ticket DTO corrispondenti
     */
    public List<TicketDto> findTicketByDateRange(LocalDateTime startTime, LocalDateTime endTime) {
        return ticketMapper.toDTOList(ticketRepository.findTicketByDateRange(startTime, endTime));
    }
}
