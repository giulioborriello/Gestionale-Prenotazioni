package com.gestionaleprenotazioni.formerjob.EventTest;

import com.gestionaleprenotazioni.formerjob.Dto.EventDto;
import com.gestionaleprenotazioni.formerjob.Model.Type;
import com.gestionaleprenotazioni.formerjob.Service.EventService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
public class EventControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EventService eventService;

    // 🔹 Test 1: Trova evento per nome
    @Test
    void testFindByName() throws Exception {

        // 1️⃣ Creo un DipendenteDto realistico
        EventDto dto = new EventDto();
        dto.setId(1);
        dto.setName("ITALIASPAGNA");

        // 2️⃣ Dico al mock di restituire questo DTO quando il service viene chiamato
        when(eventService
                .findByName("ITALIASPAGNA"))
                .thenReturn(dto);

        // 3️⃣ Faccio la chiamata GET al controller
        mockMvc.perform(get("/Event/findByName")
                        .param("name", "ITALIASPAGNA"))
                .andExpect(status().isOk()) // verifica il codice 200
                // 🔹 verifico che i dati restituiti siano corretti
                .andExpect(jsonPath("$.name").value("ITALIASPAGNA"));
    }

    // 🔹 Test 2: Trova Evento per descrizione
    @Test
    void testFindByDescription() throws Exception {

        // 1️⃣ Creo un DipendenteDto realistico
        EventDto dto = new EventDto();
        dto.setId(1);
        dto.setDescription("KKAJWHDJEJBDEUBCUJE");

        // 2️⃣ Dico al mock di restituire questo DTO quando il service viene chiamato
        when(eventService
                .findByDescription("KKAJWHDJEJBDEUBCUJE"))
                .thenReturn(dto);

        // 3️⃣ Faccio la chiamata GET al controller
        mockMvc.perform(get("/Event/findByDescription")
                        .param("description", "KKAJWHDJEJBDEUBCUJE"))
                .andExpect(status().isOk()) // verifica il codice 200
                // 🔹 verifico che i dati restituiti siano corretti
                .andExpect(jsonPath("$.description").value("KKAJWHDJEJBDEUBCUJE"));
    }

    // 🔹 Test 3: Trova eventi per tipo di evento
    @Test
    void testFindByType() throws Exception {

        EventDto dto = new EventDto();
        dto.setId(1);
        dto.setType(Type.CINEMA);

        when(eventService.findByType(Type.CINEMA))
                .thenReturn(List.of(dto));

        mockMvc.perform(get("/Event/findByType")
                        .param("type", Type.CINEMA.name()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].type").value(Type.CINEMA.name()));
    }

    // 🔹 Test 4: Trova Evento per luogo
    @Test
    void testFindByLocation() throws Exception {

        // 1️⃣ Creo un DipendenteDto realistico
        EventDto dto = new EventDto();
        dto.setId(1);
        dto.setLocation("Napoli");

        // 2️⃣ Dico al mock di restituire questo DTO quando il service viene chiamato
        when(eventService
                .findByLocation("Napoli"))
                .thenReturn(Collections.singletonList(dto));

        // 3️⃣ Faccio la chiamata GET al controller
        mockMvc.perform(get("/Event/findByLocation")
                        .param("location", "Napoli"))
                .andExpect(status().isOk()) // verifica il codice 200
                // 🔹 verifico che i dati restituiti siano corretti
                .andExpect(jsonPath("$[0].location").value("Napoli"));
    }

    // 🔹 Test 5: Trova Evento tra due date
    @Test
    void testFindByDataBetween() throws Exception {

        // 1️⃣ Creo DTO
        EventDto dto = new EventDto();
        dto.setId(1);

        // 2️⃣ Date di test
        String start = "2024-01-01";
        String end = "2024-12-31";

        // 3️⃣ Mock service
        when(eventService.findByDateBetween(any(Date.class), any(Date.class)))
                .thenReturn(List.of(dto));

        // 4️⃣ Chiamata
        mockMvc.perform(get("/Event/findByDataBetween")
                        .param("startDate", start)
                        .param("endDate", end))
                .andExpect(status().isOk())
                // 🔹 lista -> uso $[0]
                .andExpect(jsonPath("$[0].id").value(1));
    }

    // 🔹 Test 6: Trova Evento dopo una certa data
    @Test
    void testFindByDataAfter() throws Exception {

        EventDto dto = new EventDto();
        dto.setId(2);

        String data = "2024-06-01";

        when(eventService.findByDateAfter(any(Date.class)))
                .thenReturn(List.of(dto));

        mockMvc.perform(get("/Event/findByDataAfter")
                        .param("data", data))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(2));
    }

    // 🔹 Test 7: Trova Evento prima di una certa data
    @Test
    void testFindByDataBefore() throws Exception {

        EventDto dto = new EventDto();
        dto.setId(3);

        String data = "2024-06-01";

        when(eventService.findByDateBefore(any(Date.class)))
                .thenReturn(List.of(dto));

        mockMvc.perform(get("/Event/findByDataBefore")
                        .param("data", data))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(3));
    }

}
