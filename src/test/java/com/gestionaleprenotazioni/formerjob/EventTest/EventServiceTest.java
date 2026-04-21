package com.gestionaleprenotazioni.formerjob.EventTest;

import com.gestionaleprenotazioni.formerjob.Dto.EventDto;
import com.gestionaleprenotazioni.formerjob.Mapper.EventMapper;
import com.gestionaleprenotazioni.formerjob.Model.Event;
import com.gestionaleprenotazioni.formerjob.Model.Type;
import com.gestionaleprenotazioni.formerjob.Repository.EventRepository;
import com.gestionaleprenotazioni.formerjob.Service.EventService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EventServiceTest {
    //Questi test dimostrano che tutte le funzionalità funzionano come previsto.
// Non serve avere il database reale, perché usiamo mock per simulare dati.
// È un modo per garantire affidabilità del sistema.
    @Mock
    private EventRepository eventRepository;

    @Mock
    private EventMapper eventMapper;

    @InjectMocks
    private EventService eventService;

    private Event event;
    private EventDto eventDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        event = new Event();
        event.setId(1);
        event.setName("Cinema Night");
        event.setDescription("Film Marvel");
        event.setType(Type.CINEMA);
        event.setLocation("Roma");
        event.setDate(new Date());

        eventDto = new EventDto();
        eventDto.setId(1);
        eventDto.setName("Cinema Night");
        eventDto.setDescription("Film Marvel");
        eventDto.setType(Type.CINEMA);
        eventDto.setLocation("Roma");
        eventDto.setDate(new Date());
    }

    // 🔹 Test 1: Trova evento per nome
    @Test
    void testFindByName() {
        when(eventRepository.findByName("Cinema Night")).thenReturn(event);
        when(eventMapper.toDTO(event)).thenReturn(eventDto);

        EventDto result = eventService.findByName("Cinema Night");

        assertNotNull(result);
        assertEquals("Cinema Night", result.getName());
    }

    // 🔹 Test 2: Trova Evento per descrizione
    @Test
    void testFindByDescription() {
        when(eventRepository.findByDescription("Film Marvel")).thenReturn(event);
        when(eventMapper.toDTO(event)).thenReturn(eventDto);

        EventDto result = eventService.findByDescription("Film Marvel");

        assertEquals("Film Marvel", result.getDescription());
    }

    // 🔹 Test 3: Trova eventi per tipo di evento
    @Test
    void testFindByType() {
        when(eventRepository.findByType(Type.CINEMA)).thenReturn(List.of(event));
        when(eventMapper.toDTOList(List.of(event))).thenReturn(List.of(eventDto));

        List<EventDto> result = eventService.findByType(Type.CINEMA);

        assertEquals(1, result.size());
        assertEquals(Type.CINEMA, result.get(0).getType());
    }

    // 🔹 Test 4: Trova Evento per luogo
    @Test
    void testFindByLocation() {
        when(eventRepository.findByLocation("Roma")).thenReturn(List.of(event));
        when(eventMapper.toDTOList(List.of(event))).thenReturn(List.of(eventDto));

        List<EventDto> result = eventService.findByLocation("Roma");

        assertEquals("Roma", result.get(0).getLocation());
    }

    // 🔹 Test 5: Trova Evento tra due date
    @Test
    void testFindByDataBetween() {
        Date start = new Date();
        Date end = new Date();

        when(eventRepository.findByDateBetween(start, end)).thenReturn(List.of(event));
        when(eventMapper.toDTOList(List.of(event))).thenReturn(List.of(eventDto));

        List<EventDto> result = eventService.findByDateBetween(start, end);

        assertFalse(result.isEmpty());
    }

    // 🔹 Test 6: Trova Evento dopo una certa data
    @Test
    void testFindByDataAfter() {
        Date data = new Date();

        when(eventRepository.findByDateAfter(data)).thenReturn(List.of(event));
        when(eventMapper.toDTOList(List.of(event))).thenReturn(List.of(eventDto));

        List<EventDto> result = eventService.findByDateAfter(data);

        assertEquals(1, result.size());
    }

    // 🔹 Test 7: Trova Evento prima di una certa data
    @Test
    void testFindByDataBefore() {
        Date data = new Date();

        when(eventRepository.findByDateBefore(data)).thenReturn(List.of(event));
        when(eventMapper.toDTOList(List.of(event))).thenReturn(List.of(eventDto));

        List<EventDto> result = eventService.findByDateBefore(data);

        assertEquals(1, result.size());
    }

    // 🔹 Test 8: Trova Evento per biglietti venduti
    @Test
    void testFindBySelledTickets() {
        when(eventRepository.findBySelledTickets(50)).thenReturn(List.of(event));
        when(eventMapper.toDTOList(List.of(event))).thenReturn(List.of(eventDto));

        List<EventDto> result = eventService.findBySelledTickets(50);

        assertEquals(1, result.size());
        verify(eventRepository).findBySelledTickets(50);
    }

    // 🔹 Test 9: Trova Evento per biglietti venduti minori di X
    @Test
    void testFindBySelledTicketsLessThan() {
        when(eventRepository.findBySelledTicketsLessThan(100)).thenReturn(List.of(event));
        when(eventMapper.toDTOList(List.of(event))).thenReturn(List.of(eventDto));

        List<EventDto> result = eventService.findBySelledTicketsLessThan(100);

        assertEquals(1, result.size());
        verify(eventRepository).findBySelledTicketsLessThan(100);
    }

    // 🔹 Test 10: Trova Evento per biglietti venduti maggiori di X
    @Test
    void testFindBySelledTicketsGreaterThan() {
        when(eventRepository.findBySelledTicketsGreaterThan(30)).thenReturn(List.of(event));
        when(eventMapper.toDTOList(List.of(event))).thenReturn(List.of(eventDto));

        List<EventDto> result = eventService.findBySelledTicketsGreaterThan(30);

        assertEquals(1, result.size());
        verify(eventRepository).findBySelledTicketsGreaterThan(30);
    }

    // 🔹 Test 11: Trova Evento per prezzo del biglietto
    @Test
    void testFindByTicketPrice() {
        when(eventRepository.findByTicketPrice(50.0)).thenReturn(List.of(event));
        when(eventMapper.toDTOList(List.of(event))).thenReturn(List.of(eventDto));

        List<EventDto> result = eventService.findByTicketPrice(50.0);

        assertEquals(1, result.size());
        verify(eventRepository).findByTicketPrice(50.0);
    }

    // 🔹 Test 12: Creazione evento Ok
    @Test
    void testBuildEventFromDto_OK() {
        eventDto.setTicketPrice(50.0);

        when(eventMapper.toEntity(eventDto)).thenReturn(event);

        Event result = eventService.buildEventFromDto(eventDto);

        assertNotNull(result);
        assertEquals(250, result.getMaxTickets());
        assertEquals(0, result.getSelledTickets());
    }

    // 🔹 Test 13: Creazione evento Dto NULL
    @Test
    void testBuildEventFromDto_NullDto() {
        assertThrows(RuntimeException.class, () -> {
            eventService.buildEventFromDto(null);
        });
    }

    // 🔹 Test 14: Creazione nome dell'evento NULL
    @Test
    void testBuildEventFromDto_NameNull() {
        eventDto.setName(null);

        assertThrows(RuntimeException.class, () -> {
            eventService.buildEventFromDto(eventDto);
        });
    }

    // 🔹 Test 15: Creazione prezzo del biglietto dell'evento NULL
    @Test
    void testBuildEventFromDto_TicketPriceNull() {
        eventDto.setTicketPrice(null);

        assertThrows(RuntimeException.class, () -> {
            eventService.buildEventFromDto(eventDto);
        });
    }

    // 🔹 Test 16: Trova i 5 eventi più remunerativi
    @Test
    void testFindTop5MostRemunerative() {

        when(eventRepository.findTop5ByOrderBySelledTicketsDescDateDesc())
                .thenReturn(List.of(event));

        when(eventMapper.toDTOList(List.of(event)))
                .thenReturn(List.of(eventDto));

        List<EventDto> result = eventService.findTop5MostRemunerative();

        assertNotNull(result);
        assertEquals(1, result.size());

        verify(eventRepository).findTop5ByOrderBySelledTicketsDescDateDesc();
    }
}
