package com.gestionaleprenotazioni.formerjob.TicketTest;

import com.gestionaleprenotazioni.formerjob.Controller.TicketController;
import com.gestionaleprenotazioni.formerjob.Dto.TicketDto;
import com.gestionaleprenotazioni.formerjob.Service.TicketService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TicketControllerTest {

    @Mock
    private TicketService ticketService;

    @InjectMocks
    private TicketController ticketController;

    private TicketDto ticketDto;
    private LocalDateTime fixedDate;

    @BeforeEach
    public void init() {
        fixedDate = LocalDateTime.of(2026, 3, 31, 10, 0);
        ticketDto = new TicketDto();
        ticketDto.setTicketId(1);
        ticketDto.setName("Mario");
        ticketDto.setSurname("Rossi");
        ticketDto.setPrice(20.0);
        ticketDto.setCreationDate(fixedDate);
    }

    @Test
    void findTicketByNameAndSurname_Test() {
        String name = "Mario";
        String surname = "Rossi";
        when(ticketService.findTicketByNameAndSurname(name, surname))
                .thenReturn(Collections.singletonList(ticketDto));

        List<TicketDto> result = ticketController.findTicketByNameAndSurname(name, surname);

        assertExpectedTicketDto(result);
        verify(ticketService, times(1)).findTicketByNameAndSurname(name, surname);
    }

    @Test
    void findTicketByCreationDate_Test() {
        when(ticketService.findTicketByCreationDate(fixedDate))
                .thenReturn(Collections.singletonList(ticketDto));

        List<TicketDto> result = ticketController.findTicketByCreationDate(fixedDate);

        assertExpectedTicketDto(result);
        verify(ticketService, times(1)).findTicketByCreationDate(fixedDate);
    }

    @Test
    void findTicketByPriceGreaterThanEqual_Test() {
        Double targetPrice = 15.0;
        when(ticketService.findTicketByPriceGreaterThanEqual(targetPrice))
                .thenReturn(Collections.singletonList(ticketDto));

        List<TicketDto> result = ticketController.findTicketByPriceGreaterThanEqual(targetPrice);

        assertExpectedTicketDto(result);
        verify(ticketService, times(1)).findTicketByPriceGreaterThanEqual(targetPrice);
    }

    @Test
    void findTicketByPriceLessThanEqual_Test() {
        Double targetPrice = 30.0;
        when(ticketService.findTicketByPriceLessThanEqual(targetPrice))
                .thenReturn(Collections.singletonList(ticketDto));

        List<TicketDto> result = ticketController.findTicketByPriceLessThanEqual(targetPrice);

        assertExpectedTicketDto(result);
        verify(ticketService, times(1)).findTicketByPriceLessThanEqual(targetPrice);
    }

    @Test
    void findTicketByPriceRange_Test() {
        Double initialPrice = 10.0;
        Double endPrice = 50.0;
        when(ticketService.findTicketByPriceRange(initialPrice, endPrice))
                .thenReturn(Collections.singletonList(ticketDto));

        List<TicketDto> result = ticketController.findTicketByPriceRange(initialPrice, endPrice);

        assertExpectedTicketDto(result);
        verify(ticketService, times(1)).findTicketByPriceRange(initialPrice, endPrice);
    }

    @Test
    void findTicketByDateRange_Test() {
        LocalDateTime start = fixedDate.minusDays(1);
        LocalDateTime end = fixedDate.plusDays(1);
        when(ticketService.findTicketByDateRange(start, end))
                .thenReturn(Collections.singletonList(ticketDto));

        List<TicketDto> result = ticketController.findTicketByDateRange(start, end);

        assertExpectedTicketDto(result);
        verify(ticketService, times(1)).findTicketByDateRange(start, end);
    }

    private void assertExpectedTicketDto(List<TicketDto> result) {
        assertEquals(1, result.size());
        TicketDto actualDto = result.get(0);
        assertEquals("Mario", actualDto.getName());
        assertEquals("Rossi", actualDto.getSurname());
        assertEquals(20.0, actualDto.getPrice());
        assertEquals(fixedDate, actualDto.getCreationDate());
    }
}