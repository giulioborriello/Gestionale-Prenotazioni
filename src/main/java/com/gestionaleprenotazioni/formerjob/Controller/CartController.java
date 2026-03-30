package com.gestionaleprenotazioni.formerjob.Controller;

import com.gestionaleprenotazioni.formerjob.Dto.CartDto;
import com.gestionaleprenotazioni.formerjob.Service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart") // Prefisso per tutti gli endpoint di questa classe
public class CartController {

    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    // Recupera tutti i carrelli
    @GetMapping("/all")
    public ResponseEntity<List<CartDto>> getAllCarts() {
        return ResponseEntity.ok(cartService.findAll());
    }

    // Recupera un carrello specifico per ID
    @GetMapping("/{id}")
    public ResponseEntity<CartDto> getCartById(@PathVariable Integer id) {
        return ResponseEntity.ok(cartService.findById(id));
    }

    // Recupera il carrello di un utente specifico (usando il metodo JPQL che abbiamo fatto)
    @GetMapping("/user/{userId}")
    public ResponseEntity<CartDto> getCartByUserId(@PathVariable Integer userId) {
        return ResponseEntity.ok(cartService.findByUserId(userId));
    }

    // Elimina un carrello
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCart(@PathVariable Integer id) {
        cartService.delete(id);
        return ResponseEntity.noContent().build();
    }
}