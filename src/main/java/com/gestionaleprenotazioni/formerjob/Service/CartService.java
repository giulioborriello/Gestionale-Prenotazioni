package com.gestionaleprenotazioni.formerjob.Service;

import com.gestionaleprenotazioni.formerjob.Dto.CartDto;
import com.gestionaleprenotazioni.formerjob.Mapper.CartMapper; // Assumendo che esista un CartMapper
import com.gestionaleprenotazioni.formerjob.Model.Cart;
import com.gestionaleprenotazioni.formerjob.Repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartService extends AbstractService<Cart, CartDto> {

    private final CartRepository cartRepository;
    private final CartMapper cartMapper;

    @Autowired
    public CartService(JpaRepository<Cart, Integer> repository, CartRepository cartRepository, CartMapper cartMapper) {
        // Passiamo al super-costruttore il repository e il mapper (che funge da converter)
        super(repository, cartMapper);
        this.cartRepository = cartRepository;
        this.cartMapper = cartMapper;
    }

    // JPQL: Recupera il carrello tramite ID Utente
    public CartDto findByUserId(Integer userId) {
        Optional<Cart> cart = cartRepository.findByUserIdJPQL(userId);
        return cart.map(cartMapper::toDTO).orElse(null);
    }

    // DERIVATO: Trova carrelli senza pagamento
    public List<CartDto> findByPaymentIsNull() {
        return cartRepository.findByPaymentIsNull()
                .stream()
                .map(cartMapper::toDTO)
                .toList();
    }

    // DERIVATO: Trova carrelli sopra una certa soglia di prezzo
    public List<CartDto> findByTotalPriceGreaterThan(Double price) {
        return cartRepository.findByTotalPriceGreaterThan(price)
                .stream()
                .map(cartMapper::toDTO)
                .toList();
    }
}