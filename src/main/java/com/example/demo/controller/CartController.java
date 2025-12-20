package com.example.demo.controller;

import com.example.demo.model.Cart;
import com.example.demo.service.CartService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/carts")
@Tag(name = "Cart Controller")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    // POST /api/carts/{userId} – Create cart
    @PostMapping("/{userId}")
    public Cart createCart(@PathVariable Long userId) {
        return cartService.createCart(userId);
    }

    // GET /api/carts/{id} – Get cart by id
    @GetMapping("/{id}")
    public Cart getCart(@PathVariable Long id) {
        return cartService.getCartById(id);
    }

    // GET /api/carts/user/{userId} – Get active cart by user
    @GetMapping("/user/{userId}")
    public Cart getCartByUser(@PathVariable Long userId) {
        return cartService.getActiveCartForUser(userId);
    }

    // PUT /api/carts/{id}/deactivate – Deactivate cart
    @PutMapping("/{id}/deactivate")
    public void deactivateCart(@PathVariable Long id) {
        cartService.deactivateCart(id);
    }
}
