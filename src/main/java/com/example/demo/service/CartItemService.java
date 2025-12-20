package com.example.demo.service;

import com.example.demo.model.CartItem;

import java.util.List;

public interface CartItemService {

    // Add item to cart
    CartItem addItemToCart(CartItem item);

    // Update cart item (quantity)
    CartItem updateItem(CartItem item);

    // Get all items for a cart
    List<CartItem> getItemsForCart(Long cartId);

    // Remove item from cart
    void removeItem(Long id);
}
