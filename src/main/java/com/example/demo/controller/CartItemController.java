package com.example.demo.controller;

import com.example.demo.model.CartItem;
import com.example.demo.service.CartItemService;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<CartItem> addItem(@RequestBody CartItem item) {
        CartItem saved = cartItemService.addItemToCart(item);
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CartItem> updateItem(@PathVariable Long id,
                                               @RequestBody CartItem item) {
        item.setId(id);
        CartItem updated = cartItemService.updateItem(item);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/cart/{cartId}")
    public ResponseEntity<List<CartItem>> getItems(@PathVariable Long cartId) {
        return ResponseEntity.ok(cartItemService.getItemsForCart(cartId));
    }

  
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeItem(@PathVariable Long id) {
        cartItemService.removeItem(id);
        return ResponseEntity.noContent().build();
    }
}
