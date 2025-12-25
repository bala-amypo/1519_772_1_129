package com.example.demo.controller;

import com.example.demo.model.Cart;
import com.example.demo.service.CartService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/carts")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    // POST /api/carts/user/{userId} – Create cart
    @PostMapping("/user/{userId}")
    public ResponseEntity<Cart> createCart(@PathVariable Long userId) {
        Cart cart = cartService.createCart(userId);
        return ResponseEntity.ok(cart);
    }

    // GET /api/carts/{id} – Get cart by id
    @GetMapping("/{id}")
    public ResponseEntity<Cart> getCartById(@PathVariable Long id) {
        try {
            Cart cart = cartService.getCartById(id); // add this wrapper in service if not present
            return ResponseEntity.ok(cart);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    // GET /api/carts/user/{userId} – Get cart by user (active cart)
    @GetMapping("/user/{userId}")
    public ResponseEntity<Cart> getActiveCart(@PathVariable Long userId) {
        Cart cart = cartService.getActiveCartForUser(userId);
        return ResponseEntity.ok(cart);
    }

    // PUT /api/carts/{id}/deactivate – Deactivate cart
    @PutMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivateCart(@PathVariable Long id) {
        cartService.deactivateCart(id); // simple wrapper; implement in service if needed
        return ResponseEntity.noContent().build();
    }
}
