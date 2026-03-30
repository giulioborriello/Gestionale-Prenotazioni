package com.gestionaleprenotazioni.formerjob.Service;

import com.gestionaleprenotazioni.formerjob.Dto.EventDto;
import com.gestionaleprenotazioni.formerjob.Mapper.EventMapper;
import com.gestionaleprenotazioni.formerjob.Model.Event;
import com.gestionaleprenotazioni.formerjob.Repository.EventRepository;
import org.springframework.stereotype.Service;

@Service
public class EventService extends AbstractService<Event, EventDto> {

    private final EventMapper eventMapper;
    private final EventRepository eventRepository;

    protected EventService(EventMapper eventMapper, EventRepository eventRepository) {
        super(eventRepository, eventMapper);
        this.eventMapper = eventMapper;
        this.eventRepository = eventRepository;
        //this.userRepository = userRepository;
    }

    // Aggiungere i metodi che utilizzeremo per il nostro applicativo
}
