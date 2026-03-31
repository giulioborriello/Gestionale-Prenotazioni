package PlaceTest;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;

import com.gestionaleprenotazioni.formerjob.Controller.PlaceController;
import com.gestionaleprenotazioni.formerjob.Service.PlaceService;
import com.gestionaleprenotazioni.formerjob.Dto.PlaceDto;

import java.util.List;

//test del controller placontroller
@WebMvcTest(PlaceController.class)

//disabilita sicurezza se ce Spring Security attiv
@AutoConfigureMockMvc(addFilters = false)
public class PlaceControllerTest {

    //simula le chiamate HTTP
    @Autowired
    private MockMvc mockMvc;

    //mock del service (niente DB)
    @MockBean
    private PlaceService placeService;

    @Test
    public void testGetAllPlaces() throws Exception {

        //creo dato finto
        PlaceDto dto = new PlaceDto(1, "A1", false, "STANDARD", null);

        //simulo risposta service
        when(placeService.getAll()).thenReturn(List.of(dto));

        //chiamata GET place
        mockMvc.perform(get("/Place"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetPlaceById() throws Exception {

        //dato finto
        PlaceDto dto = new PlaceDto(1, "A1", false, "STANDARD", null);

        //mock service
        when(placeService.read(1)).thenReturn(dto);

        //chiamata GET place1
        mockMvc.perform(get("/Place/1"))
                .andExpect(status().isOk());
    }
}