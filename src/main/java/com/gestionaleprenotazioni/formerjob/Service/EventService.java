package com.gestionaleprenotazioni.formerjob.Service;

import com.gestionaleprenotazioni.formerjob.Dto.EventDto;
import com.gestionaleprenotazioni.formerjob.Mapper.EventMapper;
import com.gestionaleprenotazioni.formerjob.Model.Event;
import com.gestionaleprenotazioni.formerjob.Model.Type;
import com.gestionaleprenotazioni.formerjob.Repository.EventRepository;
import org.springframework.stereotype.Service;

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

    // 🔹 Metodo per trovare l'evento per nome
    public EventDto findByName(String name) {
        return eventMapper.toDTO(eventRepository.findByName(name));
    }

    // 🔹 Metodo per trovare l'evento per nome
    public EventDto findByDescription(String description) {
        return eventMapper.toDTO(eventRepository.findByDescription(description));
    }

    // 🔹 Metodo per trovare l'evento per nome
    public List<EventDto> findByType(Type type) {
        return eventMapper.toDTOList(eventRepository.findByType(type));
    }

    // 🔹 Metodo per trovare l'evento per nome
    public List<EventDto> findByLocation(String location) {
        return eventMapper.toDTOList(eventRepository.findByLocation(location));
    }

    // 🔹 Metodo per trovare l'evento per nome
    public List<EventDto> findByDateBetween(Date startDate, Date endDate) {
        return eventMapper.toDTOList(eventRepository.findByDateBetween(startDate, endDate));
    }

    // 🔹 Metodo per trovare l'evento per nome
    public List<EventDto> findByDateAfter(Date data) {
        return eventMapper.toDTOList(eventRepository.findByDateAfter(data));
    }

    // 🔹 Metodo per trovare l'evento per nome
    public List<EventDto> findByDateBefore(Date data) {
        return eventMapper.toDTOList(eventRepository.findByDateAfter(data));
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

}
