package com.example.demo.service.impl;

import com.example.demo.model.Cart;
import com.example.demo.model.CartItem;
import com.example.demo.model.Product;
import com.example.demo.repository.CartItemRepository;
import com.example.demo.repository.CartRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.CartItemService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartItemServiceImpl implements CartItemService {

    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    // Constructor injection (important for tests)
    public CartItemServiceImpl(
            CartItemRepository cartItemRepository,
            CartRepository cartRepository,
            ProductRepository productRepository
    ) {
        this.cartItemRepository = cartItemRepository;
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    // ✅ POST → Add item to cart
    @Override
    public CartItem addItemToCart(CartItem item) {

        if (item.getQuantity() == null || item.getQuantity() <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }

        Cart cart = cartRepository.findById(item.getCart().getId())
                .orElseThrow(() -> new EntityNotFoundException("Cart not found"));

        if (!cart.getActive()) {
            throw new IllegalArgumentException("Only active carts can accept items");
        }

        Product product = productRepository.findById(item.getProduct().getId())
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));

        if (!product.getActive()) {
            throw new IllegalArgumentException("Product is inactive");
        }

        Optional<CartItem> existingItem =
                cartItemRepository.findByCartIdAndProductId(cart.getId(), product.getId());

        if (existingItem.isPresent()) {
            CartItem existing = existingItem.get();
            existing.setQuantity(existing.getQuantity() + item.getQuantity());
            return cartItemRepository.save(existing);
        }

        item.setCart(cart);
        item.setProduct(product);
        return cartItemRepository.save(item);
    }

    // ✅ PUT → Update item quantity
    @Override
    public CartItem updateItem(CartItem item) {

        if (item.getQuantity() == null || item.getQuantity() <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }

        CartItem existing = cartItemRepository.findById(item.getId())
                .orElseThrow(() -> new EntityNotFoundException("CartItem not found"));

        existing.setQuantity(item.getQuantity());
        return cartItemRepository.save(existing);
    }

    // ✅ GET → List items
    @Override
    public List<CartItem> getItemsForCart(Long cartId) {
        return cartItemRepository.findByCartId(cartId);
    }

    // ✅ DELETE → Remove item
    @Override
    public void removeItem(Long id) {
        if (!cartItemRepository.existsById(id)) {
            throw new EntityNotFoundException("CartItem not found");
        }
        cartItemRepository.deleteById(id);
    }
}
