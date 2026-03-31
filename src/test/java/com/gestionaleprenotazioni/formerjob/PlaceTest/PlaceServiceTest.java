package com.gestionaleprenotazioni.formerjob.PlaceTest;

import com.gestionaleprenotazioni.formerjob.Dto.PlaceDto;
import com.gestionaleprenotazioni.formerjob.Mapper.PlaceMapper;
import com.gestionaleprenotazioni.formerjob.Model.Place;
import com.gestionaleprenotazioni.formerjob.Repository.PlaceRepository;
import com.gestionaleprenotazioni.formerjob.Service.PlaceService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class PlaceServiceTest {

    @Mock
    private PlaceRepository placeRepository;

    @Mock
    private PlaceMapper placeMapper;

    @InjectMocks
    private PlaceService placeService;

    @Test
    public void testGetAllPlacesService() {
        Place place = new Place();
        place.setId(1);
        place.setCode("A1");
        place.setStatus(false);

        PlaceDto dto = new PlaceDto(1, "A1", false, "STANDARD", null);

        when(placeRepository.findAll()).thenReturn(List.of(place));
        when(placeMapper.toDTOList(List.of(place))).thenReturn(List.of(dto));

        // chiamo il service
        Iterable<PlaceDto> result = placeService.getAll();

        // converto Iterable in List
        List<PlaceDto> resultList = new ArrayList<>();
        result.forEach(resultList::add);

        assertThat(resultList).isNotNull();
        assertThat(resultList.get(0).getId()).isEqualTo(1);

        verify(placeRepository, times(1)).findAll();
    }

    @Test
    public void testGetPlaceByIdService() {
        Place place = new Place();
        place.setId(1);
        place.setCode("A1");
        place.setStatus(false);

        PlaceDto dto = new PlaceDto(1, "A1", false, "STANDARD", null);

        when(placeRepository.findById(1)).thenReturn(Optional.of(place));
        when(placeMapper.toDTO(place)).thenReturn(dto);

        PlaceDto result = placeService.read(1);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1);

        verify(placeRepository, times(1)).findById(1);
    }
}