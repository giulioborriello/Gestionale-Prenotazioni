package com.gestionaleprenotazioni.formerjob.Test;

import com.gestionaleprenotazioni.formerjob.Controller.CartController;
import com.gestionaleprenotazioni.formerjob.Dto.CartDto;
import com.gestionaleprenotazioni.formerjob.Service.CartService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CartController.class)
class CartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CartService cartService;

    private CartDto createCartDto() {
        CartDto dto = new CartDto();
        dto.setId(1);
        dto.setTotalPrice(150.0);
        dto.setTicketIds(List.of(101, 102));
        return dto;
    }

    @Test
    @WithMockUser
    void testFindAll() throws Exception {
        CartDto dto = createCartDto();
        // findAll() viene ereditato da AbstractService
        when(cartService.findAll()).thenReturn(List.of(dto));

        mockMvc.perform(get("/Cart/findAll")) // Assicurati che l'URL nel Controller sia /Cart/findAll
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    @WithMockUser
    void testFindById() throws Exception {
        CartDto dto = createCartDto();
        // findById() viene ereditato da AbstractService
        when(cartService.findById(1)).thenReturn(dto);

        mockMvc.perform(get("/Cart/findById/1")) // Assicurati che l'URL nel Controller sia /Cart/findById/{id}
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @WithMockUser
    void testFindByUserId() throws Exception {
        CartDto dto = createCartDto();
        // Questo è il metodo specifico che hai scritto tu nel CartService!
        when(cartService.findByUserId(1)).thenReturn(dto);

        mockMvc.perform(get("/Cart/findByUserId/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }
}