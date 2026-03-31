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

        when(eventRepository.findByDataBetween(start, end)).thenReturn(List.of(event));
        when(eventMapper.toDTOList(List.of(event))).thenReturn(List.of(eventDto));

        List<EventDto> result = eventService.findByDataBetween(start, end);

        assertFalse(result.isEmpty());
    }

    // 🔹 Test 6: Trova Evento dopo una certa data
    @Test
    void testFindByDataAfter() {
        Date data = new Date();

        when(eventRepository.findByDataAfter(data)).thenReturn(List.of(event));
        when(eventMapper.toDTOList(List.of(event))).thenReturn(List.of(eventDto));

        List<EventDto> result = eventService.findByDataAfter(data);

        assertEquals(1, result.size());
    }

    // 🔹 Test 7: Trova Evento prima di una certa data
    @Test
    void testFindByDataBefore() {
        Date data = new Date();

        when(eventRepository.findByDataBefore(data)).thenReturn(List.of(event));
        when(eventMapper.toDTOList(List.of(event))).thenReturn(List.of(eventDto));

        List<EventDto> result = eventService.findByDataBefore(data);

        assertEquals(1, result.size());
    }
}
