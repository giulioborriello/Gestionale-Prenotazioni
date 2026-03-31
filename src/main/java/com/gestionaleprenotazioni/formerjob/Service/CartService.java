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
    public CartService(CartRepository cartRepository, CartMapper cartMapper) {
        super(cartRepository, cartMapper);
        this.cartRepository = cartRepository;
    }


    public CartDto findByUserId(Integer userId) {
        Optional<Cart> cart = cartRepository.findByUserIdJPQL(userId);
        return cart.map(mapper::toDTO).orElse(null);
    }


    public List<CartDto> findByPaymentsIsEmpty() {
        return mapper.toDTOList(cartRepository.findByPaymentIsNull());
    }


    public List<CartDto> findByTotalPriceGreaterThan(Double price) {
        return mapper.toDTOList(cartRepository.findByTotalPriceGreaterThan(price));
    }
}