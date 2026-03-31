package com.gestionaleprenotazioni.formerjob.Service;

import com.gestionaleprenotazioni.formerjob.Dto.TicketDto;
import com.gestionaleprenotazioni.formerjob.Mapper.TicketMapper;
import com.gestionaleprenotazioni.formerjob.Model.Cart;
import com.gestionaleprenotazioni.formerjob.Model.Place;
import com.gestionaleprenotazioni.formerjob.Model.Ticket;
import com.gestionaleprenotazioni.formerjob.Model.User;
import com.gestionaleprenotazioni.formerjob.Repository.TicketRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TicketService extends AbstractService<Ticket, TicketDto>{
    private TicketMapper ticketMapper;
    private TicketRepository ticketRepository;

    public TicketService(TicketMapper ticketMapper, TicketRepository ticketRepository) {
        super(ticketRepository, ticketMapper);
        this.ticketMapper = ticketMapper;
        this.ticketRepository = ticketRepository;
    }

    List<TicketDto> findTicketByNameAndSurname(String name, String surname) {
        return ticketMapper.toDTOList(ticketRepository.findTicketByNameAndSurname(name, surname));
    }

    List<TicketDto> findTicketByCreationDate(LocalDateTime creationDate) {
        return ticketMapper.toDTOList(ticketRepository.findTicketByCreationDate(creationDate));
    };

    List<TicketDto> findTicketByUser(User user) {
        return ticketMapper.toDTOList(ticketRepository.findTicketByUser(user));
    };

    List<TicketDto> findTicketByCart(Cart cart) {
        return ticketMapper.toDTOList(ticketRepository.findTicketByCart(cart));
    };

    List<TicketDto> findTicketByPlace(Place place) {
        return ticketMapper.toDTOList(ticketRepository.findTicketByPlace(place));
    };

    List<TicketDto> findTicketByPriceGreaterThanEqual(Double price) {
        return ticketMapper.toDTOList(ticketRepository.findTicketByPriceGreaterThanEqual(price));
    };

    List<TicketDto> findTicketByPriceLessThanEqual(Double priceIsLessThan) {
        return ticketMapper.toDTOList(ticketRepository.findTicketByPriceLessThanEqual(priceIsLessThan));
    };

    List<TicketDto> findTicketByPriceRange(Double price) {
        return ticketMapper.toDTOList(ticketRepository.findTicketByPriceRange(price));
    }

    List<TicketDto> findTicketByDateRange(LocalDateTime startTime, LocalDateTime endTime) {
        return ticketMapper.toDTOList(ticketRepository.findTicketByDateRange(startTime, endTime));
    }
}
