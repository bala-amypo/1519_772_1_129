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

@Service
public class DiscountServiceImpl implements DiscountService {

    private final DiscountApplicationRepository discountRepo;
    private final BundleRuleRepository bundleRuleRepo;
    private final CartRepository cartRepo;
    private final CartItemRepository cartItemRepo;

    public DiscountServiceImpl(
            DiscountApplicationRepository discountRepo,
            BundleRuleRepository bundleRuleRepo,
            CartRepository cartRepo,
            CartItemRepository cartItemRepo
    ) {
        this.discountRepo = discountRepo;
        this.bundleRuleRepo = bundleRuleRepo;
        this.cartRepo = cartRepo;
        this.cartItemRepo = cartItemRepo;
    }

    @Override
    public List<DiscountApplication> evaluateDiscounts(Long cartId) {

        Cart cart = cartRepo.findById(cartId)
                .orElseThrow(() -> new EntityNotFoundException("Cart not found"));

        if (!cart.getActive()) {
            return List.of();
        }

        List<CartItem> cartItems = cartItemRepo.findByCartId(cartId);
        List<BundleRule> rules = bundleRuleRepo.findByActiveTrue();

        discountRepo.deleteByCartId(cartId);

        List<DiscountApplication> result = new ArrayList<>();

        Set<Long> productIdsInCart = cartItems.stream()
                .map(ci -> ci.getProduct().getId())
                .collect(Collectors.toSet());

        for (BundleRule rule : rules) {

            List<Long> requiredIds = Arrays.stream(
                            rule.getRequiredProductIds().split(","))
                    .map(String::trim)
                    .map(Long::valueOf)
                    .toList();

            if (productIdsInCart.containsAll(requiredIds)) {

                BigDecimal total = BigDecimal.ZERO;

                for (CartItem item : cartItems) {
                    if (requiredIds.contains(item.getProduct().getId())) {
                        total = total.add(
                                item.getProduct().getPrice()
                                        .multiply(BigDecimal.valueOf(item.getQuantity()))
                        );
                    }
                }

                BigDecimal discountAmount =
                        total.multiply(BigDecimal.valueOf(rule.getDiscountPercentage() / 100));

                if (discountAmount.compareTo(BigDecimal.ZERO) > 0) {
                    DiscountApplication app = new DiscountApplication();
                    app.setCart(cart);
                    app.setBundleRule(rule);
                    app.setDiscountAmount(discountAmount);
                    app.setAppliedAt(LocalDateTime.now());
                    result.add(discountRepo.save(app));
                }
            }
        }

        return result;
    }

    @Override
    public DiscountApplication getById(Long id) {
        return discountRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Discount not found"));
    }

    @Override
    public List<DiscountApplication> getByCartId(Long cartId) {
        return discountRepo.findByCartId(cartId);
    }
}
