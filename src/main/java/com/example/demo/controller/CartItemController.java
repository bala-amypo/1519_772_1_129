package com.example.demo.controller;

import com.example.demo.model.CartItem;
import com.example.demo.service.CartItemService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/cart-items")
public class CartItemController {

    private final CartItemService cartItemService;

    public CartItemController(CartItemService cartItemService) {
        this.cartItemService = cartItemService;
    }

    @PostMapping
    public CartItem addItem(@RequestParam Long cartId, 
                            @RequestParam Long productId, 
                            @RequestParam Integer quantity) {
        return cartItemService.addItem(cartId, productId, quantity);
    }

    @PutMapping("/{id}")
    public CartItem updateItem(@PathVariable Long id, @RequestParam Integer quantity) {
        return cartItemService.updateItem(id, quantity);
    }

    @GetMapping("/cart/{cartId}")
    public List<CartItem> getByCart(@PathVariable Long cartId) {
        return cartItemService.getItemsForCart(cartId);
    }

    @DeleteMapping("/{id}")
    public void remove(@PathVariable Long id) {
        cartItemService.removeItem(id);
    }
}