package com.example.demo.repository;

import com.example.demo.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    Optional<CartItem> findByCartIdAndProductId(Long cartId, Long productId);

    List<CartItem> findByCartId(Long cartId);

    // real Spring Data method
    List<CartItem> findByCartIdAndQuantityGreaterThanEqual(Long cartId, Integer quantity);

    // alias method expected by tests
    default List<CartItem> findByCartIdAndMinQuantity(Long cartId, Integer minQuantity) {
        return findByCartIdAndQuantityGreaterThanEqual(cartId, minQuantity);
    }
}
