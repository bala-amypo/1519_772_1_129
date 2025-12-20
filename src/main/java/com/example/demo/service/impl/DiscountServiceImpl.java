package com.example.demo.service.impl;

import com.example.demo.model.*;
import com.example.demo.repository.*;
import com.example.demo.service.DiscountService;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service   // ⭐ REQUIRED
public class DiscountServiceImpl implements DiscountService {

    private final DiscountApplicationRepository discountRepo;
    private final BundleRuleRepository bundleRuleRepo;
    private final CartRepository cartRepo;
    private final CartItemRepository cartItemRepo;

    public DiscountServiceImpl(
            DiscountApplicationRepository discountRepo,
            BundleRuleRepository bundleRuleRepo,
            CartRepository cartRepo,
            CartItemRepository cartItemRepo) {

        this.discountRepo = discountRepo;
        this.bundleRuleRepo = bundleRuleRepo;
        this.cartRepo = cartRepo;
        this.cartItemRepo = cartItemRepo;
    }

    @Override
    public List<DiscountApplication> evaluateDiscounts(Long cartId) {

        Cart cart = cartRepo.findById(cartId)
                .orElseThrow(() -> new EntityNotFoundException("Cart not found"));

        // Inactive cart → empty list
        if (!cart.getActive()) {
            return List.of();
        }

        List<CartItem> cartItems = cartItemRepo.findByCartId(cartId);
        if (cartItems.isEmpty()) {
            return List.of();
        }

        // Clear old discounts
        discountRepo.deleteByCartId(cartId);

        // Map productId → CartItem
        Map<Long, CartItem> itemMap = cartItems.stream()
                .collect(Collectors.toMap(
                        ci -> ci.getProduct().getId(),
                        ci -> ci
                ));

        List<BundleRule> activeRules = bundleRuleRepo.findByActiveTrue();
        List<DiscountApplication> applied = new ArrayList<>();

        for (BundleRule rule : activeRules) {

            Set<Long> requiredIds = Arrays.stream(
                            rule.getRequiredProductIds().split(","))
                    .map(String::trim)
                    .map(Long::valueOf)
                    .collect(Collectors.toSet());

            
            if (!itemMap.keySet().containsAll(requiredIds)) {
                continue;
            }

            
            BigDecimal total = BigDecimal.ZERO;
            for (Long pid : requiredIds) {
                CartItem ci = itemMap.get(pid);
                BigDecimal price =
                        ci.getProduct().getPrice()
                                .multiply(BigDecimal.valueOf(ci.getQuantity()));
                total = total.add(price);
            }


            BigDecimal discountAmount = total.multiply(
                    BigDecimal.valueOf(rule.getDiscountPercentage() / 100.0)
            );

            if (discountAmount.compareTo(BigDecimal.ZERO) > 0) {
                DiscountApplication app = new DiscountApplication();
                app.setCart(cart);
                app.setBundleRule(rule);
                app.setDiscountAmount(discountAmount);
                app.setAppliedAt(LocalDateTime.now());

                applied.add(discountRepo.save(app));
            }
        }

        return applied;
    }
}
