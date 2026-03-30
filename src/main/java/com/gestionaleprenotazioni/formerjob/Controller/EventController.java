package com.gestionaleprenotazioni.formerjob.Controller;

import com.gestionaleprenotazioni.formerjob.Dto.EventDto;
import com.gestionaleprenotazioni.formerjob.Service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("Event")
@CrossOrigin(origins="http://localhost:4200")
public class EventController extends AbstractController<EventDto> {

    @Autowired
    private EventService eventService;

    // Aggiungere i controllers per il nostro applicativo

}
