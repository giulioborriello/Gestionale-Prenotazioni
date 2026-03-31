package com.gestionaleprenotazioni.formerjob.PlaceTest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gestionaleprenotazioni.formerjob.Repository.PlaceRepository;
import com.gestionaleprenotazioni.formerjob.Mapper.PlaceMapper;
import com.gestionaleprenotazioni.formerjob.Service.PlaceService;
import com.gestionaleprenotazioni.formerjob.Model.Place;
import com.gestionaleprenotazioni.formerjob.Dto.PlaceDto;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

// attiva Mockito per i test senza SpringBoot
@ExtendWith(MockitoExtension.class)
public class PlaceServiceTest {

    // mock del repository (simula il DB)
    @Mock
    private PlaceRepository placeRepository;

    // mock del mapper
    @Mock
    private PlaceMapper placeMapper;

    // iniettiamo i mock dentro il service
    @InjectMocks
    private PlaceService placeService;

    @Test
    public void testGetAllPlacesService() {

        //creo un place finto
        Place place = new Place();
        place.setId(1);
        place.setCode("A1");
        place.setStatus(false);

        //creo dto finto
        PlaceDto dto = new PlaceDto(1, "A1", false, "STANDARD", null);

        //quando il repository viene chiamat da la lista finta
        when(placeRepository.findAll()).thenReturn(List.of(place));

        //quando il mapper convert da dto
        when(placeMapper.toDTOList(List.of(place))).thenReturn(List.of(dto));

        //chiamata reale al service
        Iterable<PlaceDto> result = placeService.getAll();

        //verifico che il risultato non sia null
        assertThat(result).isNotNull();

        //verifico che il repository sia stato chiamat
        verify(placeRepository, times(1)).findAll();
    }

    @Test
    public void testGetPlaceByIdService() {

        //creo place finto
        Place place = new Place();
        place.setId(1);
        place.setCode("A1");
        place.setStatus(false);

        //creo dto finto
        PlaceDto dto = new PlaceDto(1, "A1", false, "STANDARD", null);

        //simulo findById
        when(placeRepository.findById(1)).thenReturn(Optional.of(place));

        //simulo mapper
        when(placeMapper.toDTO(place)).thenReturn(dto);

        //chiamata reale
        PlaceDto result = placeService.read(1);

        //verifiche
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1);

        //verifico che il repository sia stato chiamato
        verify(placeRepository, times(1)).findById(1);
    }
}

