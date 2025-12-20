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

    // POST /api/cart-items → Add item to cart
    @PostMapping
    public CartItem addItem(@RequestBody CartItem cartItem) {
        return cartItemService.addItemToCart(cartItem);
    }

    // PUT /api/cart-items/{id} → Update item quantity
    @PutMapping("/{id}")
    public CartItem updateItem(
            @PathVariable Long id,
            @RequestBody CartItem cartItem
    ) {
        return cartItemService.updateItem(id, cartItem);
    }

    // GET /api/cart-items/cart/{cartId} → List items
    @GetMapping("/cart/{cartId}")
    public List<CartItem> getItems(@PathVariable Long cartId) {
        return cartItemService.getItemsForCart(cartId);
    }

    // DELETE /api/cart-items/{id} → Remove item
    @DeleteMapping("/{id}")
    public void deleteItem(@PathVariable Long id) {
        cartItemService.removeItem(id);
    }
}
