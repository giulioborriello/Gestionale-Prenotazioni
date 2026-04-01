package com.gestionaleprenotazioni.formerjob.Controller;

import com.gestionaleprenotazioni.formerjob.Dto.TicketDto;
import com.gestionaleprenotazioni.formerjob.Service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("Ticket")
@CrossOrigin(origins="http://localhost:4200")
public class TicketController extends AbstractController<TicketDto> {

	@Autowired
	private TicketService ticketService;

	@GetMapping("/findByNameAndSurname")
	public List<TicketDto> findTicketByNameAndSurname(@RequestParam("name") String name,
													  @RequestParam("surname") String surname) {
		return ticketService.findTicketByNameAndSurname(name, surname);
	}

	@GetMapping("/findTicketByCreationDate")
	public List<TicketDto> findTicketByCreationDate(
			@RequestParam("creationDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime creationDate) {
		return ticketService.findTicketByCreationDate(creationDate);
	}

	@GetMapping("/findTicketByPriceGreaterThanEqual")
	public List<TicketDto> findTicketByPriceGreaterThanEqual(@RequestParam("price") Double price) {
		return ticketService.findTicketByPriceGreaterThanEqual(price);
	}

	@GetMapping("/findTicketByPriceLessThanEqual")
	public List<TicketDto> findTicketByPriceLessThanEqual(@RequestParam("price") Double price) {
		return ticketService.findTicketByPriceLessThanEqual(price);
	}

	@GetMapping("/findTicketByPriceRange")
	public List<TicketDto> findTicketByPriceRange(@RequestParam("initialPrice") Double initialPrice,
												  @RequestParam("endPrice") Double endPrice) {
		return ticketService.findTicketByPriceRange(initialPrice, endPrice);
	}

	@GetMapping("/findTicketByDateRange")
	public List<TicketDto> findTicketByDateRange(
			@RequestParam("startTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
			@RequestParam("endTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
		return ticketService.findTicketByDateRange(startTime, endTime);
	}
}
