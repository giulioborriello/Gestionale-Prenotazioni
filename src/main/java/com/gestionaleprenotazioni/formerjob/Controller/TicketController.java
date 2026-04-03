package com.gestionaleprenotazioni.formerjob.Controller;

import com.gestionaleprenotazioni.formerjob.Dto.TicketDto;
import com.gestionaleprenotazioni.formerjob.Service.TicketService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Controller REST per la gestione e la ricerca dei ticket.
 *
 * <p>Espone endpoint HTTP per recuperare ticket tramite diversi filtri
 * (anagrafica, date e range di prezzo), delegando la logica al {@link TicketService}.</p>
 */
@RestController
@RequestMapping("Ticket")
@CrossOrigin(origins="http://localhost:4200")
public class TicketController extends AbstractController<TicketDto> {

	private final TicketService ticketService;

	/**
	 * Costruttore del controller.
	 *
	 * @param ticketService servizio applicativo usato per le operazioni sui ticket
	 */
	public TicketController(TicketService ticketService) {
		super(ticketService);
		this.ticketService = ticketService;
	}

	/**
	 * Cerca i ticket in base a nome e cognome dell'utente associato.
	 *
	 * @param name nome da ricercare
	 * @param surname cognome da ricercare
	 * @return lista di ticket che corrispondono ai criteri
	 */
	@GetMapping("/findByNameAndSurname")
	public List<TicketDto> findTicketByNameAndSurname(@RequestParam("name") String name,
	                                                  @RequestParam("surname") String surname) {
		return ticketService.findTicketByNameAndSurname(name, surname);
	}

	/**
	 * Cerca i ticket per data/ora di creazione esatta.
	 *
	 * @param creationDate data/ora di creazione nel formato ISO date-time
	 * @return lista di ticket creati nella data/ora indicata
	 */
	@GetMapping("/findTicketByCreationDate")
	public List<TicketDto> findTicketByCreationDate(
			@RequestParam("creationDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime creationDate) {
		return ticketService.findTicketByCreationDate(creationDate);
	}

	/**
	 * Cerca i ticket con prezzo maggiore o uguale al valore indicato.
	 *
	 * @param price prezzo minimo (incluso)
	 * @return lista di ticket con prezzo >= {@code price}
	 */
	@GetMapping("/findTicketByPriceGreaterThanEqual")
	public List<TicketDto> findTicketByPriceGreaterThanEqual(@RequestParam("price") Double price) {
		return ticketService.findTicketByPriceGreaterThanEqual(price);
	}

	/**
	 * Cerca i ticket con prezzo minore o uguale al valore indicato.
	 *
	 * @param price prezzo massimo (incluso)
	 * @return lista di ticket con prezzo <= {@code price}
	 */
	@GetMapping("/findTicketByPriceLessThanEqual")
	public List<TicketDto> findTicketByPriceLessThanEqual(@RequestParam("price") Double price) {
		return ticketService.findTicketByPriceLessThanEqual(price);
	}

	/**
	 * Cerca i ticket in un intervallo di prezzo.
	 *
	 * @param initialPrice prezzo iniziale dell'intervallo (incluso)
	 * @param endPrice prezzo finale dell'intervallo (incluso)
	 * @return lista di ticket con prezzo compreso tra {@code initialPrice} e {@code endPrice}
	 */
	@GetMapping("/findTicketByPriceRange")
	public List<TicketDto> findTicketByPriceRange(@RequestParam("initialPrice") Double initialPrice,
	                                              @RequestParam("endPrice") Double endPrice) {
		return ticketService.findTicketByPriceRange(initialPrice, endPrice);
	}

	/**
	 * Cerca i ticket in un intervallo temporale.
	 *
	 * @param startTime istante iniziale dell'intervallo (formato ISO date-time)
	 * @param endTime istante finale dell'intervallo (formato ISO date-time)
	 * @return lista di ticket con data compresa tra {@code startTime} e {@code endTime}
	 */
	@GetMapping("/findTicketByDateRange")
	public List<TicketDto> findTicketByDateRange(
			@RequestParam("startTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
			@RequestParam("endTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
		return ticketService.findTicketByDateRange(startTime, endTime);
	}
}
