package com.gestionaleprenotazioni.formerjob.Service;

import com.gestionaleprenotazioni.formerjob.Dto.EventDto;
import com.gestionaleprenotazioni.formerjob.Dto.TicketDto;
import com.gestionaleprenotazioni.formerjob.Mapper.EventMapper;
import com.gestionaleprenotazioni.formerjob.Model.Event;
import com.gestionaleprenotazioni.formerjob.Model.Ticket;
import com.gestionaleprenotazioni.formerjob.Model.Type;
import com.gestionaleprenotazioni.formerjob.Repository.EventRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventService extends AbstractService<Event, EventDto> {

    private final EventMapper eventMapper;
    private final EventRepository eventRepository;

    protected EventService(EventMapper eventMapper, EventRepository eventRepository) {
        super(eventRepository, eventMapper);
        this.eventMapper = eventMapper;
        this.eventRepository = eventRepository;
    }

    public Event buildEventFromDto(EventDto dto) {
        if (dto == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Event is required");
        }

        if (dto.getName() == null || dto.getName().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Name of event is required");
        }

        if (dto.getDescription() == null ||  dto.getDescription().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Description of event is required");
        }

        if (dto.getLocation() == null || dto.getLocation().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Location of event is required");
        }

        if (dto.getDate() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Date of event is required");
        }

        if (dto.getType() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Type of event is required");
        }

        if (dto.getTicketPrice() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "TicketPrice of event is required");
        }

        Event event = eventMapper.toEntity(dto);

        if (event.getMaxTickets() == null) {
            event.setMaxTickets(250);
        }

        if (event.getSelledTickets() == null) {
            event.setSelledTickets(0);
        }

        return event;
    }

    // 🔹 Metodo per trovare l'evento per nome
    public List<EventDto> findByName(String name) {
        return eventRepository
                .findByNameContainingIgnoreCase(name)
                .stream()
                .map(eventMapper::toDTO)
                .toList();
    }

    // 🔹 Metodo per trovare l'evento per nome
    public List<EventDto> findByDescription(String description) {
        return eventRepository
                .findByDescriptionContainingIgnoreCase(description)
                .stream()
                .map(eventMapper::toDTO)
                .toList();
    }

    // 🔹 Metodo per trovare l'evento per nome
    public List<EventDto> findByType(Type type) {
        return eventMapper.toDTOList(eventRepository.findByType(type));
    }

    // 🔹 Metodo per trovare l'evento per nome
    public List<EventDto> findByLocation(String location) {
        return eventRepository
                .findByLocationContainingIgnoreCase(location)
                .stream()
                .map(eventMapper::toDTO)
                .toList();
    }

    // 🔹 Metodo per trovare l'evento per nome
    public List<EventDto> findByDateBetween(Date startDate, Date endDate) {
        return eventMapper.toDTOList(eventRepository.findByDateBetween(startDate, endDate));
    }

    // 🔹 Metodo per trovare l'evento per nome
    public List<EventDto> findByDateAfter(Date data) {
        return eventMapper.toDTOList(eventRepository.findByDateAfter(data));
    }

    // 🔹 Metodo per trovare l'evento prima di una certa data
    public List<EventDto> findByDateBefore(Date data) {
        return eventMapper.toDTOList(eventRepository.findByDateBefore(data));
    }

    // 🔹 Metodo per trovare l'evento per per numero di biglietti venduti
    public List<EventDto> findBySelledTickets(Integer selledTickets) {
        return eventMapper.toDTOList(eventRepository.findBySelledTickets(selledTickets));
    }

    // 🔹 Metodo per trovare l'evento con meno di X biglietti venduti
    public List<EventDto> findBySelledTicketsLessThan(Integer selledTickets) {
        return eventMapper.toDTOList(eventRepository.findBySelledTicketsLessThan(selledTickets));
    }

    // 🔹 Metodo per trovare l'evento con più di X biglietti venduti
    public List<EventDto> findBySelledTicketsGreaterThan(Integer selledTickets) {
        return eventMapper.toDTOList(eventRepository.findBySelledTicketsGreaterThan(selledTickets));
    }

    // 🔹 Metodo per trovare l'evento per il costo del biglietto
    public List<EventDto> findByTicketPrice(Double ticketPrice) {
        return eventMapper.toDTOList(eventRepository.findByTicketPrice(ticketPrice));
    }

    // 🔹 Metodo per trovare i 5 eventi più remunerativi (più venduti e più recenti)
    public List<EventDto> findTop5MostRemunerative() {
        return eventMapper.toDTOList(
                eventRepository.findTop5ByOrderBySelledTicketsDescDateDesc()
        );
    }

    // 🔹 Biglietti venduti di un singolo evento
    public Integer getSelledTicketsByEventId(Integer eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Evento non trovato"
                ));

        return event.getSelledTickets();
    }

    // 🔹 Biglietti disponibili di un singolo evento
    public Integer getAvailableTicketsByEventId(Integer eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Evento non trovato"
                ));

        return event.getMaxTickets() - event.getSelledTickets();
    }

}
