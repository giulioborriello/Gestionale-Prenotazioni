package com.gestionaleprenotazioni.formerjob.Controller;

import com.gestionaleprenotazioni.formerjob.Dto.PlaceDto;
import com.gestionaleprenotazioni.formerjob.Mapper.PlaceMapper;
import com.gestionaleprenotazioni.formerjob.Model.Place;
import com.gestionaleprenotazioni.formerjob.Model.Event;
import com.gestionaleprenotazioni.formerjob.Repository.PlaceRepository;
import com.gestionaleprenotazioni.formerjob.Repository.EventRepository;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/places")
public class PlaceController {

    private final PlaceRepository placeRepository;
    private final EventRepository eventRepository;

    public PlaceController(PlaceRepository placeRepository, EventRepository eventRepository) {
        this.placeRepository = placeRepository;
        this.eventRepository = eventRepository;
    }

    //GET tutt è post!
    @GetMapping
    public List<PlaceDto> getAll() {
        return placeRepository.findAll().stream()
                .map(PlaceMapper::toDTO)
                .collect(Collectors.toList());
    }

    //GET posto per ID
    @GetMapping("/{id}")
    public ResponseEntity<PlaceDto> getById(@PathVariable Integer id) {
        return placeRepository.findById(id)
                .map(PlaceMapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //POST new posto
    @PostMapping
    public ResponseEntity<PlaceDto> create(@RequestBody PlaceDto dto) {
        Event event = eventRepository.findById(dto.getEventId()).orElse(null);
        if (event == null) return ResponseEntity.badRequest().build();

        Place place = PlaceMapper.fromDTO(dto, event);
        placeRepository.save(place);
        return ResponseEntity.ok(PlaceMapper.toDTO(place));
    }

    //PUT aggiorn o' post
    @PutMapping("/{id}")
    public ResponseEntity<PlaceDto> update(@PathVariable Integer id, @RequestBody PlaceDto dto) {
        Place place = placeRepository.findById(id).orElse(null);
        if (place == null) return ResponseEntity.notFound().build();

        Event event = eventRepository.findById(dto.getEventId()).orElse(null);
        if (event == null) return ResponseEntity.badRequest().build();

        place.setCode(dto.getCode());
        place.setStatus(dto.isStatus());
        place.setEvent(event);

        placeRepository.save(place);
        return ResponseEntity.ok(PlaceMapper.toDTO(place));
    }

    //DELETE ò post!
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (!placeRepository.existsById(id)) return ResponseEntity.notFound().build();
        placeRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}








