package com.gestionaleprenotazioni.formerjob.Controller;

import com.gestionaleprenotazioni.formerjob.Dto.EventDto;
import com.gestionaleprenotazioni.formerjob.Model.Event;
import com.gestionaleprenotazioni.formerjob.Model.Type;
import com.gestionaleprenotazioni.formerjob.Service.EventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("Event")
@CrossOrigin(origins="http://localhost:4200")
public class EventController extends AbstractController<EventDto> {

    private final EventService eventService;

    public EventController(EventService eventService) {
        super(eventService);
        this.eventService = eventService;
    }

    @PostMapping("/buildEventFromDto")
    public Event buildEventFromDto(@RequestBody EventDto dto) {
        return eventService.buildEventFromDto(dto);
    }

    // 🔹 Per nome
    @Operation(summary = "Trova un evento per nome")
    @GetMapping("/findByName")
    public EventDto findByName(@Parameter(description = "Nome dell'evento da cercare", required = true) @RequestParam("name") String name) {
        return eventService.findByName(name);
    }

    // 🔹 Per descrizione
    @GetMapping("/findByDescription")
    public EventDto findByDescription(@RequestParam("description") String description) {
        return eventService.findByDescription(description);
    }

    // 🔹 Per tipo
    @GetMapping("/findByType")
    public List<EventDto> findByType(@RequestParam("type") Type type) {
        return eventService.findByType(type);
    }

    // 🔹 Per location
    @GetMapping("/findByLocation")
    public List<EventDto> findByLocation(@RequestParam("location") String location) {
        return eventService.findByLocation(location);
    }

    // 🔹 Tra due date
    @GetMapping("/findByDataBetween")
    public List<EventDto> findByDateBetween(
            @RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {

        return eventService.findByDateBetween(startDate, endDate);
    }

    // 🔹 Dopo una data
    @GetMapping("/findByDataAfter")
    public List<EventDto> findByDateAfter(
            @RequestParam("data") @DateTimeFormat(pattern = "yyyy-MM-dd") Date data) {

        return eventService.findByDateAfter(data);
    }

    // 🔹 Prima di una data
    @GetMapping("/findByDataBefore")
    public List<EventDto> findByDateBefore(
            @RequestParam("data") @DateTimeFormat(pattern = "yyyy-MM-dd") Date data) {

        return eventService.findByDateBefore(data);
    }

    // 🔹 Per biglietti venduti uguali a un valore
    @GetMapping("/findBySelledTickets")
    public List<EventDto> findBySelledTickets(@RequestParam("selledTickets") Integer selledTickets) {
        return eventService.findBySelledTickets(selledTickets);
    }

    // 🔹 Per biglietti venduti minori di un valore
    @GetMapping("/findBySelledTicketsLessThan")
    public List<EventDto> findBySelledTicketsLessThan(@RequestParam("selledTickets") Integer selledTickets) {
        return eventService.findBySelledTicketsLessThan(selledTickets);
    }

    // 🔹 Per biglietti venduti maggiori di un valore
    @GetMapping("/findBySelledTicketsGreaterThan")
    public List<EventDto> findBySelledTicketsGreaterThan(@RequestParam("selledTickets") Integer selledTickets) {
        return eventService.findBySelledTicketsGreaterThan(selledTickets);
    }

    // 🔹 Per prezzo biglietto
    @GetMapping("/findByTicketPrice")
    public List<EventDto> findByTicketPrice(@RequestParam("ticketPrice") Double ticketPrice) {
        return eventService.findByTicketPrice(ticketPrice);
    }

}
