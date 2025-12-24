package com.example.demo.service.impl;

import com.example.demo.model.*;
import com.example.demo.repository.*;
import com.example.demo.service.DiscountService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class DiscountServiceImpl implements DiscountService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final BundleRuleRepository bundleRuleRepository;
    private final DiscountApplicationRepository discountApplicationRepository;

    public DiscountServiceImpl(CartRepository cartRepository,
                               CartItemRepository cartItemRepository,
                               BundleRuleRepository bundleRuleRepository,
                               DiscountApplicationRepository discountApplicationRepository) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.bundleRuleRepository = bundleRuleRepository;
        this.discountApplicationRepository = discountApplicationRepository;
    }

    @Override
    public List<DiscountApplication> evaluateDiscounts(Long cartId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        // clear previous applications for this cart (optional; depends on tests)
        List<DiscountApplication> existing = discountApplicationRepository.findByCart(cart);
        discountApplicationRepository.deleteAll(existing);

        List<CartItem> items = cartItemRepository.findByCart(cart);
        List<BundleRule> activeRules = bundleRuleRepository.findByActiveTrue();

        List<DiscountApplication> result = new ArrayList<>();

        for (BundleRule rule : activeRules) {
            if (cartContainsAllRequiredProducts(items, rule)) {
                BigDecimal discountAmount = calculateDiscountAmount(items, rule);

                DiscountApplication da = new DiscountApplication();
                da.setCart(cart);
                da.setBundleRule(rule);
                da.setDiscountAmount(discountAmount);

                discountApplicationRepository.save(da);
                result.add(da);
            }
        }

        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public List<DiscountApplication> getApplicationsForCart(Long cartId) {
        return discountApplicationRepository.findByCartId(cartId);
    }



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
        // simple logic: discount on total price of qualifying products
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

        BigDecimal percentage =
                BigDecimal.valueOf(rule.getDiscountPercentage()) // 0–100
                        .divide(BigDecimal.valueOf(100));

        return total.multiply(percentage);
    }
}
