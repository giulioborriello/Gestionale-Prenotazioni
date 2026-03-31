package com.gestionaleprenotazioni.formerjob.Controller;

import com.gestionaleprenotazioni.formerjob.Dto.CartDto;
import com.gestionaleprenotazioni.formerjob.Service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")

public class CartController extends AbstractController<CartDto> {

    @Autowired
    private CartService cartService;


    @GetMapping("/user/{userId}")
    public ResponseEntity<CartDto> getCartByUserId(@PathVariable Integer userId) {

        return ResponseEntity.ok(cartService.findByUserId(userId));
    }


    @GetMapping("/details/{id}")
    public ResponseEntity<CartDto> getCartDetails(@PathVariable Integer id) {

        return ResponseEntity.ok(cartService.read(id));
    }
}