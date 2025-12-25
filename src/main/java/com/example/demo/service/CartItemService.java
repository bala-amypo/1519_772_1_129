package com.example.demo.service;

import com.example.demo.model.CartItem;

import java.util.List;

public interface CartItemService {

    CartItem addItem(Long cartId, Long productId, Integer quantity);

    CartItem updateItem(Long cartItemId, Integer quantity);

    List<CartItem> getItemsForCart(Long cartId);

    void removeItem(Long cartItemId);
}
