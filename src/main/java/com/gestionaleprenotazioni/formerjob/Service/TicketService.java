package com.gestionaleprenotazioni.formerjob.Service;

import com.gestionaleprenotazioni.formerjob.Dto.TicketDto;
import com.gestionaleprenotazioni.formerjob.Mapper.TicketMapper;
import com.gestionaleprenotazioni.formerjob.Model.Cart;
import com.gestionaleprenotazioni.formerjob.Model.Event;
import com.gestionaleprenotazioni.formerjob.Model.Place;
import com.gestionaleprenotazioni.formerjob.Model.Ticket;
import com.gestionaleprenotazioni.formerjob.Model.User;
import com.gestionaleprenotazioni.formerjob.Repository.CartRepository;
import com.gestionaleprenotazioni.formerjob.Repository.EventRepository;
import com.gestionaleprenotazioni.formerjob.Repository.PlaceRepository;
import com.gestionaleprenotazioni.formerjob.Repository.TicketRepository;
import com.gestionaleprenotazioni.formerjob.Repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TicketService extends AbstractService<Ticket, TicketDto>{
    private final TicketMapper ticketMapper;
    private final TicketRepository ticketRepository;
    private final CartRepository cartRepository;
    private final PlaceRepository placeRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    public TicketService(TicketMapper ticketMapper,
                         TicketRepository ticketRepository,
                         CartRepository cartRepository,
                         PlaceRepository placeRepository,
                         UserRepository userRepository,
                         EventRepository eventRepository) {
        super(ticketRepository, ticketMapper);
        this.ticketMapper = ticketMapper;
        this.ticketRepository = ticketRepository;
        this.cartRepository = cartRepository;
        this.placeRepository = placeRepository;
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
    }

    @Override
    public TicketDto insert(TicketDto dto) {
        Ticket ticket = buildTicketFromDto(dto);
        return ticketMapper.toDTO(ticketRepository.save(ticket));
    }

    @Override
    public TicketDto update(TicketDto dto) {
        if (dto == null || dto.getTicketId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ticketId is required for update");
        }

        if (!ticketRepository.existsById(dto.getTicketId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ticket not found with id: " + dto.getTicketId());
        }

        Ticket ticket = buildTicketFromDto(dto);
        return ticketMapper.toDTO(ticketRepository.save(ticket));
    }

    private Ticket buildTicketFromDto(TicketDto dto) {
        if (dto == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ticket payload is required");
        }

        if (dto.getEventId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "eventId is required");
        }

        Ticket ticket = ticketMapper.toEntity(dto);
        ticket.setEvent(resolveEvent(dto.getEventId()));

        if (dto.getCartId() != null) {
            ticket.setCart(resolveCart(dto.getCartId()));
        }
        if (dto.getPlaceId() != null) {
            ticket.setPlace(resolvePlace(dto.getPlaceId()));
        }
        if (dto.getUserId() != null) {
            ticket.setUser(resolveUser(dto.getUserId()));
        }

        return ticket;
    }

    private Cart resolveCart(Integer id) {
        return cartRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cart not found with id: " + id));
    }

    private Place resolvePlace(Integer id) {
        return placeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Place not found with id: " + id));
    }

    private User resolveUser(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with id: " + id));
    }

    private Event resolveEvent(Integer id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found with id: " + id));
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
