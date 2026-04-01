package com.gestionaleprenotazioni.formerjob.PlaceTest;

import com.gestionaleprenotazioni.formerjob.Controller.PlaceController;
import com.gestionaleprenotazioni.formerjob.Dto.PlaceDto;
import com.gestionaleprenotazioni.formerjob.Service.PlaceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
//CONTROLLATE RAGAZZI SE VA BENE
import java.lang.reflect.Field;
import java.util.List;
import java.util.ArrayList;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class PlaceControllerTest {

    @Mock
    private PlaceService placeService;

    @InjectMocks
    private PlaceController placeController;

    // cosi dovrei aver fixato fratm
    @BeforeEach
    void setup() throws Exception {
        Field field = placeController.getClass()
                .getSuperclass()
                .getDeclaredField("service");

        field.setAccessible(true);
        field.set(placeController, placeService);
    }

    @Test
    public void testGetAllPlacesController() {
        PlaceDto dto = new PlaceDto(1, "Posto A1", "A1", false, "STANDARD", null);

        when(placeService.getAll()).thenReturn(List.of(dto));

        Iterable<PlaceDto> result = placeController.getAll();

        List<PlaceDto> list = new ArrayList<>();
        result.forEach(list::add);

        assertThat(list).isNotNull();
        assertThat(list.get(0).getId()).isEqualTo(1);

        verify(placeService, times(1)).getAll();
    }

    @Test
    public void testGetPlaceByIdController() {
        PlaceDto dto = new PlaceDto(1, "Posto A1", "A1", false, "STANDARD", null);

        when(placeService.read(1)).thenReturn(dto);

        PlaceDto result = placeController.read(1);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1);

        verify(placeService, times(1)).read(1);
    }
}