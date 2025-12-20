package com.example.demo.service.impl;

import com.example.demo.model.CartItem;
import com.example.demo.repository.CartItemRepository;
import com.example.demo.service.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartItemServiceImpl implements CartItemService {

    @Autowired
    private CartItemRepository cartItemRepository;

    @Override
    public CartItem addCartItem(CartItem cartItem) {
        return cartItemRepository.save(cartItem);
    }

    @Override
    public CartItem updateCartItem(Long id, CartItem cartItem) {
        CartItem existingItem = cartItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("CartItem not found"));
        existingItem.setProductId(cartItem.getProductId());
        existingItem.setQuantity(cartItem.getQuantity());
        return cartItemRepository.save(existingItem);
    }

    @Override
    public List<CartItem> getCartItemsByCartId(Long cartId) {
        return cartItemRepository.findByCartId(cartId);
    }

    @Override
    public void removeCartItem(Long id) {
        cartItemRepository.deleteById(id);
    }
}
