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

    // POST /api/cart-items – Add item to cart
    @PostMapping
    public ResponseEntity<CartItem> addItem(@RequestBody CartItem item) {
        CartItem saved = cartItemService.addItemToCart(item);
        return ResponseEntity.ok(saved);
    }

    // PUT /api/cart-items/{id} – Update item quantity
    @PutMapping("/{id}")
    public ResponseEntity<CartItem> updateItem(@PathVariable Long id,
                                               @RequestBody CartItem item) {
        item.setId(id);
        CartItem updated = cartItemService.updateItem(item);
        return ResponseEntity.ok(updated);
    }

    // GET /api/cart-items/cart/{cartId} – List items
    @GetMapping("/cart/{cartId}")
    public ResponseEntity<List<CartItem>> getItems(@PathVariable Long cartId) {
        return ResponseEntity.ok(cartItemService.getItemsForCart(cartId));
    }

    // DELETE /api/cart-items/{id} – Remove item
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeItem(@PathVariable Long id) {
        cartItemService.removeItem(id);
        return ResponseEntity.noContent().build();
    }
}
