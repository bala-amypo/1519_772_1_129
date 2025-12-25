package com.example.demo.service.impl;

import com.example.demo.model.BundleRule;
import com.example.demo.model.Cart;
import com.example.demo.model.CartItem;
import com.example.demo.model.DiscountApplication;
import com.example.demo.repository.BundleRuleRepository;
import com.example.demo.repository.CartItemRepository;
import com.example.demo.repository.CartRepository;
import com.example.demo.repository.DiscountApplicationRepository;
import com.example.demo.service.DiscountService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class DiscountServiceImpl implements DiscountService {

    private final DiscountApplicationRepository discountApplicationRepository;
    private final BundleRuleRepository bundleRuleRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    // constructor in the exact order tests expect
    public DiscountServiceImpl(DiscountApplicationRepository discountApplicationRepository,
                               BundleRuleRepository bundleRuleRepository,
                               CartRepository cartRepository,
                               CartItemRepository cartItemRepository) {
        this.discountApplicationRepository = discountApplicationRepository;
        this.bundleRuleRepository = bundleRuleRepository;
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
    }

    @Override
    public List<DiscountApplication> evaluateDiscounts(Long cartId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new EntityNotFoundException("Cart not found"));

        // if cart is inactive, return empty list (tests check this)
        if (Boolean.FALSE.equals(cart.getActive())) {
            return List.of();
        }

        // clear previous discount applications first
        discountApplicationRepository.deleteByCartId(cartId);

        // load current items and active rules
        List<CartItem> items = cartItemRepository.findByCartId(cartId);
        List<BundleRule> activeRules = bundleRuleRepository.findByActiveTrue();

        List<DiscountApplication> result = new ArrayList<>();

        for (BundleRule rule : activeRules) {
            if (cartContainsAllRequiredProducts(items, rule)) {
                BigDecimal discountAmount = calculateDiscountAmount(items, rule);

                if (discountAmount.compareTo(BigDecimal.ZERO) > 0) {
                    DiscountApplication app = new DiscountApplication();
                    app.setCart(cart);
                    app.setBundleRule(rule);
                    app.setDiscountAmount(discountAmount);

                    discountApplicationRepository.save(app);
                    result.add(app);
                }
            }
        }

        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public List<DiscountApplication> getApplicationsForCart(Long cartId) {
        return discountApplicationRepository.findByCartId(cartId);
    }

    @Override
    @Transactional(readOnly = true)
    public DiscountApplication getApplicationById(Long id) {
        return discountApplicationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Discount application not found"));
    }

    // ---------- helper methods ----------

    private boolean cartContainsAllRequiredProducts(List<CartItem> items,
                                                    BundleRule rule) {
        String csv = rule.getRequiredProductIds();
        if (csv == null || csv.isBlank()) {
            return false;
        }

        String[] ids = csv.split(",");
        for (String idStr : ids) {
            Long requiredId = Long.parseLong(idStr.trim());
            boolean found = items.stream()
                    .anyMatch(ci -> ci.getProduct().getId().equals(requiredId));
            if (!found) {
                return false;
            }
        }
        return true;
    }

    private BigDecimal calculateDiscountAmount(List<CartItem> items,
                                               BundleRule rule) {
        String[] ids = rule.getRequiredProductIds().split(",");
        BigDecimal total = BigDecimal.ZERO;

        for (String idStr : ids) {
            Long requiredId = Long.parseLong(idStr.trim());
            for (CartItem ci : items) {
                if (ci.getProduct().getId().equals(requiredId)) {
                    BigDecimal price = ci.getProduct().getPrice();
                    BigDecimal qty = BigDecimal.valueOf(ci.getQuantity());
                    total = total.add(price.multiply(qty));
                }
            }
        }

        BigDecimal percentage = BigDecimal
                .valueOf(rule.getDiscountPercentage())   // 0–100
                .divide(BigDecimal.valueOf(100));

        return total.multiply(percentage);
    }
}
