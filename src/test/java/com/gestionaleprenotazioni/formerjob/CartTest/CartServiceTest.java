package com.gestionaleprenotazioni.formerjob.CartTest;

import com.gestionaleprenotazioni.formerjob.Dto.CartDto;
import com.gestionaleprenotazioni.formerjob.Mapper.CartMapper;
import com.gestionaleprenotazioni.formerjob.Model.Cart;
import com.gestionaleprenotazioni.formerjob.Repository.CartRepository;
import com.gestionaleprenotazioni.formerjob.Service.CartService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CartServiceTest {

    @Mock
    private CartRepository cartRepository;

    @Mock
    private CartMapper cartMapper;

    @InjectMocks
    private CartService cartService;

    // metodo di supporto per creare un Cart (entity) di test
    private Cart createCart() {
        Cart cart = new Cart();
        cart.setId(1);
        cart.setTotalPrice(150.0);
        return cart;
    }

    // metodo di supporto per creare un CartDto di test
    private CartDto createCartDto() {
        CartDto dto = new CartDto();
        dto.setId(1);
        dto.setTotalPrice(150.0);
        dto.setTicketIds(List.of(101, 102));
        return dto;
    }

    @Test
    void testGetAll() {
        Cart cart = createCart();
        CartDto dto = createCartDto();

        // simuliamo repository.findAll() e mapper.toDTOList()
        when(cartRepository.findAll()).thenReturn(List.of(cart));
        when(cartMapper.toDTOList(List.of(cart))).thenReturn(List.of(dto));

        Iterable<CartDto> result = cartService.getAll();

        assertThat(result).isNotNull();
        verify(cartRepository).findAll();
        verify(cartMapper).toDTOList(List.of(cart));
    }

    @Test
    void testRead() {
        Cart cart = createCart();
        CartDto dto = createCartDto();

        // simuliamo repository.findById() e mapper.toDTO()
        when(cartRepository.findById(1)).thenReturn(Optional.of(cart));
        when(cartMapper.toDTO(cart)).thenReturn(dto);

        CartDto result = cartService.read(1);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1);
        verify(cartRepository).findById(1);
        verify(cartMapper).toDTO(cart);
    }

    @Test
    void testInsert() {
        Cart cart = createCart();
        CartDto dto = createCartDto();

        // simuliamo mapper.toEntity(), repository.save() e mapper.toDTO()
        when(cartMapper.toEntity(dto)).thenReturn(cart);
        when(cartRepository.save(cart)).thenReturn(cart);
        when(cartMapper.toDTO(cart)).thenReturn(dto);

        CartDto result = cartService.insert(dto);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1);
        verify(cartMapper).toEntity(dto);
        verify(cartRepository).save(cart);
        verify(cartMapper).toDTO(cart);
    }

    @Test
    void testUpdate() {
        Cart cart = createCart();
        CartDto dto = createCartDto();

        // update usa save() esattamente come insert()
        when(cartMapper.toEntity(dto)).thenReturn(cart);
        when(cartRepository.save(cart)).thenReturn(cart);
        when(cartMapper.toDTO(cart)).thenReturn(dto);

        CartDto result = cartService.update(dto);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1);
        verify(cartMapper).toEntity(dto);
        verify(cartRepository).save(cart);
        verify(cartMapper).toDTO(cart);
    }

    @Test
    void testDelete() {
        // delete non ritorna nulla, verifichiamo solo che venga chiamato
        cartService.delete(1);
        verify(cartRepository).deleteById(1);
    }

    @Test
    void testFindByUserId() {
        Cart cart = createCart();
        CartDto dto = createCartDto();

        // metodo specifico di CartService che usa la query JPQL
        when(cartRepository.findByUserIdJPQL(1)).thenReturn(Optional.of(cart));
        when(cartMapper.toDTO(cart)).thenReturn(dto);

        CartDto result = cartService.findByUserId(1);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1);
        verify(cartRepository).findByUserIdJPQL(1);
        verify(cartMapper).toDTO(cart);
    }

    @Test
    void testFindByUserIdNotFound() {
        // caso in cui l'utente non ha un carrello — deve tornare null
        when(cartRepository.findByUserIdJPQL(99)).thenReturn(Optional.empty());

        CartDto result = cartService.findByUserId(99);

        assertThat(result).isNull();
        verify(cartRepository).findByUserIdJPQL(99);
    }
}