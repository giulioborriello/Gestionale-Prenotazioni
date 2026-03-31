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

    public List<TicketDto> findTicketByNameAndSurname(String name, String surname) {
        return ticketMapper.toDTOList(ticketRepository.findTicketByNameAndSurname(name, surname));
    }

    public List<TicketDto> findTicketByCreationDate(LocalDateTime creationDate) {
        return ticketMapper.toDTOList(ticketRepository.findTicketByCreationDate(creationDate));
    }

    public List<TicketDto> findTicketByUser(User user) {
        return ticketMapper.toDTOList(ticketRepository.findTicketByUser(user));
    }

    public List<TicketDto> findTicketByCart(Cart cart) {
        return ticketMapper.toDTOList(ticketRepository.findTicketByCart(cart));
    }

    public List<TicketDto> findTicketByPlace(Place place) {
        return ticketMapper.toDTOList(ticketRepository.findTicketByPlace(place));
    }

    public List<TicketDto> findTicketByPriceGreaterThanEqual(Double price) {
        return ticketMapper.toDTOList(ticketRepository.findTicketByPriceGreaterThanEqual(price));
    }

    public List<TicketDto> findTicketByPriceLessThanEqual(Double priceIsLessThan) {
        return ticketMapper.toDTOList(ticketRepository.findTicketByPriceLessThanEqual(priceIsLessThan));
    }

    public List<TicketDto> findTicketByPriceRange(Double initialPrice, Double endPrice) {
        return ticketMapper.toDTOList(ticketRepository.findTicketByPriceRange(initialPrice, endPrice));
    }

    public List<TicketDto> findTicketByDateRange(LocalDateTime startTime, LocalDateTime endTime) {
        return ticketMapper.toDTOList(ticketRepository.findTicketByDateRange(startTime, endTime));
    }
}
