package com.example.demo.controller;

import com.example.demo.model.CartItem;
import com.example.demo.service.CartItemService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart-items")
@Tag(name = "Cart Items")
public class CartItemController {

    private final CartItemService cartItemService;

    public CartItemController(CartItemService cartItemService) {
        this.cartItemService = cartItemService;
    }


    @PostMapping
    public ResponseEntity<CartItem> addItem(@RequestParam Long cartId,
                                            @RequestParam Long productId,
                                            @RequestParam Integer quantity) {
        CartItem saved = cartItemService.addItem(cartId, productId, quantity);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

 
    @PutMapping("/{id}")
    public ResponseEntity<CartItem> updateItem(@PathVariable Long id,
                                               @RequestParam Integer quantity) {
        CartItem updated = cartItemService.updateItem(id, quantity);
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
