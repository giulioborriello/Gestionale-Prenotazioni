package com.gestionaleprenotazioni.formerjob.TicketTest;

import com.gestionaleprenotazioni.formerjob.Dto.TicketDto;
import com.gestionaleprenotazioni.formerjob.Mapper.TicketMapper;
import com.gestionaleprenotazioni.formerjob.Model.Event;
import com.gestionaleprenotazioni.formerjob.Model.Ticket;
import com.gestionaleprenotazioni.formerjob.Model.User;
import com.gestionaleprenotazioni.formerjob.Repository.EventRepository;
import com.gestionaleprenotazioni.formerjob.Repository.TicketRepository;
import com.gestionaleprenotazioni.formerjob.Repository.UserRepository;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TicketServiceTest {

    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private EventRepository eventRepository;

    @Spy
    private TicketMapper ticketMapper = new TicketMapper();

    @InjectMocks
    private TicketService ticketService;

    private Ticket ticketEntity;
    private Event eventEntity;
    private User userEntity;
    private LocalDateTime fixedDate;

    @BeforeEach
    public void init() {
        fixedDate = LocalDateTime.of(2026, 3, 31, 10, 0);
        ticketEntity = new Ticket();
        ticketEntity.setId(1);
        ticketEntity.setName("Mario");
        ticketEntity.setSurname("Rossi");
        ticketEntity.setPrice(20.0);
        ticketEntity.setCreationDate(fixedDate);

        eventEntity = new Event();
        eventEntity.setId(10);
        eventEntity.setName("Evento Test");
        eventEntity.setSelledTickets(5);

        userEntity = new User();
        userEntity.setId(7);
        userEntity.setName("Mario");
        userEntity.setSurname("Rossi");
    }

    @Test
    void insert_ShouldIncrementEventSelledTickets_WhenTicketIsSaved() {
        TicketDto dto = new TicketDto();
        dto.setName("Mario");
        dto.setSurname("Rossi");
        dto.setPrice(20.0);
        dto.setCreationDate(fixedDate);
        dto.setUserId(userEntity.getId());
        dto.setEventId(eventEntity.getId());

        when(userRepository.findById(userEntity.getId())).thenReturn(Optional.of(userEntity));
        when(eventRepository.findById(eventEntity.getId())).thenReturn(Optional.of(eventEntity));
        when(ticketRepository.save(any(Ticket.class))).thenAnswer(invocation -> {
            Ticket savedTicket = invocation.getArgument(0);
            savedTicket.setId(99);
            return savedTicket;
        });

        TicketDto result = ticketService.insert(dto);

        assertNotNull(result);
        assertEquals(6, eventEntity.getSelledTickets());
        verify(eventRepository, times(1)).findById(eventEntity.getId());
        verify(userRepository, times(1)).findById(userEntity.getId());
        verify(ticketRepository, times(1)).save(argThat(ticket ->
                ticket.getEvent() == eventEntity
                        && ticket.getUser() == userEntity
                        && "Mario".equals(ticket.getName())
                        && "Rossi".equals(ticket.getSurname())
                        && Double.valueOf(20.0).equals(ticket.getPrice())
                        && fixedDate.equals(ticket.getCreationDate())
        ));
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