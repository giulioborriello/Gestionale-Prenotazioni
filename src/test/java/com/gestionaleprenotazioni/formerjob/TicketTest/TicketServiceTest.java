package com.gestionaleprenotazioni.formerjob.TicketTest;

import com.gestionaleprenotazioni.formerjob.Dto.TicketDto;
import com.gestionaleprenotazioni.formerjob.Mapper.TicketMapper;
import com.gestionaleprenotazioni.formerjob.Model.Cart;
import com.gestionaleprenotazioni.formerjob.Model.Place;
import com.gestionaleprenotazioni.formerjob.Model.Ticket;
import com.gestionaleprenotazioni.formerjob.Model.User;
import com.gestionaleprenotazioni.formerjob.Repository.TicketRepository;
import com.gestionaleprenotazioni.formerjob.Service.TicketService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TicketServiceTest {

    @Mock
    private TicketRepository ticketRepository;

    @Spy
    private TicketMapper ticketMapper = new TicketMapper();

    @InjectMocks
    private TicketService ticketService;

    private Ticket ticketEntity;
    private LocalDateTime fixedDate;

    @BeforeEach
    public void init() {
        fixedDate = LocalDateTime.of(2026, 3, 31, 10, 0);

        ticketEntity = new Ticket();
        ticketEntity.setTicketId(1);
        ticketEntity.setName("Mario");
        ticketEntity.setSurname("Rossi");
        ticketEntity.setPrice(20.0);
        ticketEntity.setCreationDate(fixedDate);
    }

    @Test
    void findTicketByNameAndSurname_Test() {
        String name = "Mario";
        String surname = "Rossi";

        when(ticketRepository.findTicketByNameAndSurname(name, surname))
                .thenReturn(Collections.singletonList(ticketEntity));

        List<TicketDto> list = ticketService.findTicketByNameAndSurname(name, surname);

        assertExpectedTicketDto(list);
        verify(ticketRepository, times(1)).findTicketByNameAndSurname(name, surname);
    }

    @Test
    void findTicketByCreationDate_Test() {
        when(ticketRepository.findTicketByCreationDate(fixedDate))
                .thenReturn(Collections.singletonList(ticketEntity));

        List<TicketDto> result = ticketService.findTicketByCreationDate(fixedDate);

        assertExpectedTicketDto(result);
        verify(ticketRepository, times(1)).findTicketByCreationDate(fixedDate);
    }

    @Test
    void findTicketByUser_Test() {
        User user = new User();
        when(ticketRepository.findTicketByUser(user))
                .thenReturn(Collections.singletonList(ticketEntity));

        List<TicketDto> result = ticketService.findTicketByUser(user);

        assertExpectedTicketDto(result);
        verify(ticketRepository, times(1)).findTicketByUser(user);
    }

    @Test
    void findTicketByCart_Test() {
        Cart cart = new Cart();
        when(ticketRepository.findTicketByCart(cart))
                .thenReturn(Collections.singletonList(ticketEntity));

        List<TicketDto> result = ticketService.findTicketByCart(cart);

        assertExpectedTicketDto(result);
        verify(ticketRepository, times(1)).findTicketByCart(cart);
    }

    @Test
    void findTicketByPlace_Test() {
        Place place = new Place();
        when(ticketRepository.findTicketByPlace(place))
                .thenReturn(Collections.singletonList(ticketEntity));

        List<TicketDto> result = ticketService.findTicketByPlace(place);

        assertExpectedTicketDto(result);
        verify(ticketRepository, times(1)).findTicketByPlace(place);
    }

    @Test
    void findTicketByPriceGreaterThanEqual_Test() {
        Double targetPrice = 15.0;
        when(ticketRepository.findTicketByPriceGreaterThanEqual(targetPrice))
                .thenReturn(Collections.singletonList(ticketEntity));

        List<TicketDto> result = ticketService.findTicketByPriceGreaterThanEqual(targetPrice);

        assertExpectedTicketDto(result);
        verify(ticketRepository, times(1)).findTicketByPriceGreaterThanEqual(targetPrice);
    }

    @Test
    void findTicketByPriceLessThanEqual_Test() {
        Double targetPrice = 30.0;
        when(ticketRepository.findTicketByPriceLessThanEqual(targetPrice))
                .thenReturn(Collections.singletonList(ticketEntity));

        List<TicketDto> result = ticketService.findTicketByPriceLessThanEqual(targetPrice);

        assertExpectedTicketDto(result);
        verify(ticketRepository, times(1)).findTicketByPriceLessThanEqual(targetPrice);
    }

    @Test
    void findTicketByPriceRange_Test() {
        Double initialPrice = 10.0;
        Double endPrice = 50.0;
        when(ticketRepository.findTicketByPriceRange(initialPrice, endPrice))
                .thenReturn(Collections.singletonList(ticketEntity));

        List<TicketDto> result = ticketService.findTicketByPriceRange(initialPrice, endPrice);

        assertExpectedTicketDto(result);
        verify(ticketRepository, times(1)).findTicketByPriceRange(initialPrice, endPrice);
    }

    @Test
    void findTicketByDateRange_Test() {
        LocalDateTime start = fixedDate.minusDays(1);
        LocalDateTime end = fixedDate.plusDays(1);
        when(ticketRepository.findTicketByDateRange(start, end))
                .thenReturn(Collections.singletonList(ticketEntity));

        List<TicketDto> result = ticketService.findTicketByDateRange(start, end);

        assertExpectedTicketDto(result);
        verify(ticketRepository, times(1)).findTicketByDateRange(start, end);
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