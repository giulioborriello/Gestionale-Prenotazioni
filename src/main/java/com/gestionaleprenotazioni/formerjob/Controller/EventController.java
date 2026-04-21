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

    @Operation(summary = "Crea evento")
    @PostMapping("/buildEventFromDto")
    public Event buildEventFromDto(@Parameter(description = "Crea evento", required = true)@RequestBody EventDto dto) {
        return eventService.buildEventFromDto(dto);
    }

    // 🔹 Per nome
    @Operation(summary = "Trova un evento per nome")
    @GetMapping("/findByName")
    public EventDto findByName(@Parameter(description = "Nome dell'evento da cercare", required = true) @RequestParam("name") String name) {
        return eventService.findByName(name);
    }

    // 🔹 Per descrizione
    @Operation(summary = "Trova un evento per descrizione")
    @GetMapping("/findByDescription")
    public EventDto findByDescription(@Parameter(description = "Descrizione dell'evento da cercare", required = true)@RequestParam("description") String description) {
        return eventService.findByDescription(description);
    }

    // 🔹 Per tipo
    @Operation(summary = "Trova un evento per tipo")
    @GetMapping("/findByType")
    public List<EventDto> findByType(@Parameter(description = "Tipo dell'evento da cercare", required = true)@RequestParam("type") Type type) {
        return eventService.findByType(type);
    }

    // 🔹 Per location
    @Operation(summary = "Trova un evento per luogo")
    @GetMapping("/findByLocation")
    public List<EventDto> findByLocation(@Parameter(description = "Luogo dell'evento da cercare", required = true)@RequestParam("location") String location) {
        return eventService.findByLocation(location);
    }

    // 🔹 Tra due date
    @Operation(summary = "Trova un evento tra due date")
    @GetMapping("/findByDataBetween")
    public List<EventDto> findByDateBetween(
            @Parameter(description = "Prima data", required = true)@RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @Parameter(description = "Seconda data", required = true)@RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {

        return eventService.findByDateBetween(startDate, endDate);
    }

    // 🔹 Dopo una data
    @Operation(summary = "Trova un evento dopo una data")
    @GetMapping("/findByDataAfter")
    public List<EventDto> findByDateAfter(
            @Parameter(description = "Trova un evento dopo una data", required = true)@RequestParam("data") @DateTimeFormat(pattern = "yyyy-MM-dd") Date data) {

        return eventService.findByDateAfter(data);
    }

    // 🔹 Prima di una data
    @Operation(summary = "Trova un evento prima di una data")
    @GetMapping("/findByDataBefore")
    public List<EventDto> findByDateBefore(
            @Parameter(description = "Trova un evento prima di una data", required = true)@RequestParam("data") @DateTimeFormat(pattern = "yyyy-MM-dd") Date data) {

        return eventService.findByDateBefore(data);
    }

    // 🔹 Per biglietti venduti uguali a un valore
    @Operation(summary = "Trova un evento per numero di biglietti venduti")
    @GetMapping("/findBySelledTickets")
    public List<EventDto> findBySelledTickets(@Parameter(description = "Trova un evento per numero di biglietti venduti", required = true)@RequestParam("selledTickets") Integer selledTickets) {
        return eventService.findBySelledTickets(selledTickets);
    }

    // 🔹 Per biglietti venduti minori di un valore
    @Operation(summary = "Trova un evento per biglietti venduti minori di un valore")
    @GetMapping("/findBySelledTicketsLessThan")
    public List<EventDto> findBySelledTicketsLessThan(@Parameter(description = "Trova un evento Per biglietti venduti minori di un valore", required = true)@RequestParam("selledTickets") Integer selledTickets) {
        return eventService.findBySelledTicketsLessThan(selledTickets);
    }

    // 🔹 Per biglietti venduti maggiori di un valore
    @Operation(summary = "Trova un evento per biglietti venduti maggiori di un valore")
    @GetMapping("/findBySelledTicketsGreaterThan")
    public List<EventDto> findBySelledTicketsGreaterThan(@Parameter(description = "Trova un evento Per biglietti venduti maggiori di un valore", required = true)@RequestParam("selledTickets") Integer selledTickets) {
        return eventService.findBySelledTicketsGreaterThan(selledTickets);
    }

    // 🔹 Per prezzo biglietto
    @Operation(summary = "Trova un evento per prezzo del biglietto")
    @GetMapping("/findByTicketPrice")
    public List<EventDto> findByTicketPrice(@Parameter(description = "Trova un evento per prezzo del biglietto", required = true)@RequestParam("ticketPrice") Double ticketPrice) {
        return eventService.findByTicketPrice(ticketPrice);
    }

    // 🔹 Top 5 eventi più remunerativi (più venduti e più recenti)
    @Operation(summary = "Trova i 5 eventi più remunerativi")
    @GetMapping("/findTop5MostRemunerative")
    public List<EventDto> findTop5MostRemunerative() {
        return eventService.findTop5MostRemunerative();
    }

}
