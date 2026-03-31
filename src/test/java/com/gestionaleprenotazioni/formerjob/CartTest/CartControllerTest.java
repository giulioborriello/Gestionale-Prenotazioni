package com.gestionaleprenotazioni.formerjob.CartTest;

import com.gestionaleprenotazioni.formerjob.Controller.CartController;
import com.gestionaleprenotazioni.formerjob.Dto.CartDto;
import com.gestionaleprenotazioni.formerjob.Service.CartService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CartControllerTest {

    @Mock
    private CartService cartService;

    @InjectMocks
    private CartController cartController;

    private CartDto createCartDto() {
        CartDto dto = new CartDto();
        dto.setId(1);
        dto.setTotalPrice(150.0);
        dto.setTicketIds(List.of(101, 102));
        return dto;
    }

    @Test
    void testGetCartDetails() {
        CartDto dto = createCartDto();
        // getCartDetails() chiama cartService.read() internamente
        when(cartService.read(1)).thenReturn(dto);

        ResponseEntity<CartDto> result = cartController.getCartDetails(1);

        assertThat(result).isNotNull();
        assertThat(result.getBody().getId()).isEqualTo(1);
        verify(cartService).read(1);
    }

    @Test
    void testGetCartByUserId() {
        CartDto dto = createCartDto();
        // getCartByUserId() chiama cartService.findByUserId() internamente
        when(cartService.findByUserId(1)).thenReturn(dto);

        ResponseEntity<CartDto> result = cartController.getCartByUserId(1);

        assertThat(result).isNotNull();
        assertThat(result.getBody().getId()).isEqualTo(1);
        verify(cartService).findByUserId(1);
    }
}