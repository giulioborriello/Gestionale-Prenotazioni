package com.gestionaleprenotazioni.formerjob.EventTest;

import com.gestionaleprenotazioni.formerjob.Controller.EventController;
import com.gestionaleprenotazioni.formerjob.Dto.EventDto;
import com.gestionaleprenotazioni.formerjob.Model.Type;
import com.gestionaleprenotazioni.formerjob.Service.EventService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EventControllerTest {

    @Mock
    private EventService eventService;

    @InjectMocks
    private EventController eventController;

    // 🔹 Test 1: Trova evento per nome
    @Test
    void testFindByName() {
        String name = "ITALIASPAGNA";

        EventDto dto = new EventDto();
        dto.setId(1);
        dto.setName(name);

        when(eventService.findByName(name)).thenReturn(dto);

        EventDto result = eventController.findByName(name);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo(name);

        verify(eventService).findByName(name);
    }

    // 🔹 Test 2: Trova evento per descrizione
    @Test
    void testFindByDescription() {
        String desc = "KKAJWHDJEJBDEUBCUJE";

        EventDto dto = new EventDto();
        dto.setDescription(desc);

        when(eventService.findByDescription(desc)).thenReturn(dto);

        EventDto result = eventController.findByDescription(desc);

        assertThat(result.getDescription()).isEqualTo(desc);

        verify(eventService).findByDescription(desc);
    }

    // 🔹 Test 3: Trova per tipo
    @Test
    void testFindByType() {
        EventDto dto = new EventDto();
        dto.setType(Type.CINEMA);

        when(eventService.findByType(Type.CINEMA))
                .thenReturn(List.of(dto));

        List<EventDto> result = eventController.findByType(Type.CINEMA);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getType()).isEqualTo(Type.CINEMA);

        verify(eventService).findByType(Type.CINEMA);
    }

    // 🔹 Test 4: Trova per location
    @Test
    void testFindByLocation() {
        String location = "Napoli";

        EventDto dto = new EventDto();
        dto.setLocation(location);

        when(eventService.findByLocation(location))
                .thenReturn(List.of(dto));

        List<EventDto> result = eventController.findByLocation(location);

        assertThat(result.get(0).getLocation()).isEqualTo(location);

        verify(eventService).findByLocation(location);
    }

    // 🔹 Test 5: Tra due date
    @Test
    void testFindByDataBetween() {
        EventDto dto = new EventDto();
        dto.setId(1);

        Date start = new Date();
        Date end = new Date();

        when(eventService.findByDateBetween(start, end))
                .thenReturn(List.of(dto));

        List<EventDto> result = eventController.findByDateBetween(start, end);

        assertThat(result).hasSize(1);

        verify(eventService).findByDateBetween(start, end);
    }

    // 🔹 Test 6: Dopo una data
    @Test
    void testFindByDataAfter() {
        EventDto dto = new EventDto();
        dto.setId(2);

        Date data = new Date();

        when(eventService.findByDateAfter(data))
                .thenReturn(List.of(dto));

        List<EventDto> result = eventController.findByDateAfter(data);

        assertThat(result.get(0).getId()).isEqualTo(2);

        verify(eventService).findByDateAfter(data);
    }

    // 🔹 Test 7: Prima di una data
    @Test
    void testFindByDataBefore() {
        EventDto dto = new EventDto();
        dto.setId(3);

        Date data = new Date();

        when(eventService.findByDateBefore(data))
                .thenReturn(List.of(dto));

        List<EventDto> result = eventController.findByDateBefore(data);

        assertThat(result.get(0).getId()).isEqualTo(3);

        verify(eventService).findByDateBefore(data);
    }

    // 🔹 Test 8: Trova eventi con biglietti venduti uguali
    @Test
    void testFindBySelledTickets() {
        Integer selledTickets = 50;

        EventDto dto = new EventDto();
        dto.setSelledTickets(selledTickets);

        when(eventService.findBySelledTickets(selledTickets))
                .thenReturn(List.of(dto));

        List<EventDto> result = eventController.findBySelledTickets(selledTickets);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getSelledTickets()).isEqualTo(selledTickets);

        verify(eventService).findBySelledTickets(selledTickets);
    }


    // 🔹 Test 9: Trova eventi con biglietti venduti minori di un valore
    @Test
    void testFindBySelledTicketsLessThan() {
        Integer limit = 100;

        EventDto dto = new EventDto();
        dto.setSelledTickets(80);

        when(eventService.findBySelledTicketsLessThan(limit))
                .thenReturn(List.of(dto));

        List<EventDto> result = eventController.findBySelledTicketsLessThan(limit);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getSelledTickets()).isLessThan(limit);

        verify(eventService).findBySelledTicketsLessThan(limit);
    }


    // 🔹 Test 10: Trova eventi con biglietti venduti maggiori di un valore
    @Test
    void testFindBySelledTicketsGreaterThan() {
        Integer limit = 30;

        EventDto dto = new EventDto();
        dto.setSelledTickets(60);

        when(eventService.findBySelledTicketsGreaterThan(limit))
                .thenReturn(List.of(dto));

        List<EventDto> result = eventController.findBySelledTicketsGreaterThan(limit);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getSelledTickets()).isGreaterThan(limit);

        verify(eventService).findBySelledTicketsGreaterThan(limit);
    }


    // 🔹 Test 11: Trova eventi per prezzo biglietto
    @Test
    void testFindByTicketPrice() {
        Double price = 50.0;

        EventDto dto = new EventDto();
        dto.setTicketPrice(price);

        when(eventService.findByTicketPrice(price))
                .thenReturn(List.of(dto));

        List<EventDto> result = eventController.findByTicketPrice(price);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getTicketPrice()).isEqualTo(price);

        verify(eventService).findByTicketPrice(price);
    }
}