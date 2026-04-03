package com.gestionaleprenotazioni.formerjob.Mapper;

import com.gestionaleprenotazioni.formerjob.Dto.TicketDto;
import com.gestionaleprenotazioni.formerjob.Model.Event;
import com.gestionaleprenotazioni.formerjob.Model.Ticket;
import com.gestionaleprenotazioni.formerjob.Model.User;
import org.springframework.stereotype.Component;

/**
 * Mapper tra {@link TicketDto} e {@link Ticket}.
 *
 * <p>Questa classe converte i dati tra il livello API (DTO) e il livello dominio/persistenza (Entity).
 * La conversione delle relazioni ({@link User} e {@link Event}) viene fatta tramite soli ID,
 * creando oggetti "stub" utili per associare i riferimenti senza caricare subito le entita complete.</p>
 */
@Component
public class TicketMapper extends AbstractMapper<TicketDto, Ticket> {

    /**
     * Converte un {@link TicketDto} in una entity {@link Ticket}.
     *
     * <p>Se presenti, {@code userId} ed {@code eventId} vengono mappati su oggetti
     * {@link User} e {@link Event} con solo ID valorizzato.</p>
     *
     * @param ticketDto DTO sorgente
     * @return entity risultante, oppure {@code null} se il DTO in input e null
     */
    @Override
    public Ticket toEntity(TicketDto ticketDto) {
        if (ticketDto == null) {
            return null;
        }

        Ticket ticket = new Ticket();
        ticket.setId(ticketDto.getTicketId());
        ticket.setName(ticketDto.getName());
        ticket.setSurname(ticketDto.getSurname());
        ticket.setPrice(ticketDto.getPrice());
        ticket.setCreationDate(ticketDto.getCreationDate());

        if (ticketDto.getUserId() != null) {
            User user = new User();
            user.setId(ticketDto.getUserId());
            ticket.setUser(user);
        }

        if (ticketDto.getEventId() != null) {
            Event event = new Event();
            event.setId(ticketDto.getEventId());
            ticket.setEvent(event);
        }

        return ticket;
    }

    /**
     * Converte una entity {@link Ticket} in {@link TicketDto}.
     *
     * <p>Le relazioni con utente ed evento vengono riportate nel DTO come soli ID
     * (campi {@code userId} e {@code eventId}).</p>
     *
     * @param ticket entity sorgente
     * @return DTO risultante, oppure {@code null} se l'entity in input e null
     */
    @Override
    public TicketDto toDTO(Ticket ticket) {
        if (ticket == null) {
            return null;
        }

        TicketDto dto = new TicketDto();
        dto.setTicketId(ticket.getId());
        dto.setName(ticket.getName());
        dto.setSurname(ticket.getSurname());
        dto.setPrice(ticket.getPrice());
        dto.setCreationDate(ticket.getCreationDate());

        if (ticket.getUser() != null) {
            dto.setUserId(ticket.getUser().getId());
        }
        if (ticket.getEvent() != null) {
            dto.setEventId(ticket.getEvent().getId());
        }

        return dto;
    }
}
