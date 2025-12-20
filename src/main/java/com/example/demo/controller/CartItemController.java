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

    // 1️⃣ POST /api/cart-items → Add item to cart
    @PostMapping
    public CartItem addItemToCart(@RequestBody CartItem cartItem) {
        return cartItemService.addItemToCart(cartItem);
    }

    // 2️⃣ PUT /api/cart-items/{id} → Update item quantity
    @PutMapping("/{id}")
    public CartItem updateItem(
            @PathVariable Long id,
            @RequestBody CartItem updatedItem
    ) {
        updatedItem.setId(id);
        return cartItemService.updateItem(updatedItem);
    }

    // 3️⃣ GET /api/cart-items/cart/{cartId} → List items
    @GetMapping("/cart/{cartId}")
    public List<CartItem> getItemsByCart(@PathVariable Long cartId) {
        return cartItemService.getItemsForCart(cartId);
    }

    // 4️⃣ DELETE /api/cart-items/{id} → Remove item
    @DeleteMapping("/{id}")
    public void deleteItem(@PathVariable Long id) {
        cartItemService.removeItem(id);
    }
}
