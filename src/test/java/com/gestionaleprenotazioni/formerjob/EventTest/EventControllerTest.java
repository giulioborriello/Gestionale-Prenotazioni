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
}