package com.gestionaleprenotazioni.formerjob.Service;

import com.gestionaleprenotazioni.formerjob.Dto.CartDto;
import com.gestionaleprenotazioni.formerjob.Mapper.CartMapper;
import com.gestionaleprenotazioni.formerjob.Model.Cart;
import com.gestionaleprenotazioni.formerjob.Repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartService extends AbstractService<Cart, CartDto> {


    private final CartRepository cartRepository;

    @Autowired
    // Semplifichiamo il costruttore: iniettiamo direttamente il CartRepository
    public CartService(CartRepository cartRepository, CartMapper cartMapper) {

        super(cartRepository, cartMapper);
        this.cartRepository = cartRepository;
    }

    // 1. JPQL: Recupera il carrello tramite ID Utente
    public CartDto findByUserId(Integer userId) {
        Optional<Cart> cart = cartRepository.findByUserIdJPQL(userId);
        // Usiamo "mapper" che è il nome del campo nell'AbstractService
        return cart.map(mapper::toDTO).orElse(null);
    }

    // 2. DERIVATO: Trova carrelli senza pagamento
    public List<CartDto> findByPaymentIsNull() {
        return mapper.toDTOList(cartRepository.findByPaymentIsNull());
    }

    // 3. DERIVATO: Trova carrelli sopra una certa soglia di prezzo
    public List<CartDto> findByTotalPriceGreaterThan(Double price) {
        return mapper.toDTOList(cartRepository.findByTotalPriceGreaterThan(price));
    }
}