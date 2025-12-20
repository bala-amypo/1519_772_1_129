package com.example.demo.repository;

import com.example.demo.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    Optional<CartItem> findByCartIdAndProductId(Long cartId, Long productId);

    List<CartItem> findByCartId(Long cartId);

    @Query("SELECT c FROM CartItem c WHERE c.cart.id = :cartId AND c.quantity >= :minQuantity")
    List<CartItem> findByCartIdAndMinQuantity(Long cartId, Integer minQuantity);
}
