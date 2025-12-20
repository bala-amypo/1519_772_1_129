package com.example.demo.service.impl;

import com.example.demo.model.DiscountApplication;
import com.example.demo.model.CartItem;
import com.example.demo.service.DiscountService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DiscountServiceImpl implements DiscountService {

    @Override
    public DiscountApplication evaluateDiscount(Long cartId) {
        // Example logic for evaluation
        DiscountApplication discount = new DiscountApplication();
        discount.setId(1L);
        discount.setCartId(cartId);
        discount.setDiscountAmount(50.0);  // static example, replace with real logic
        discount.setDescription("Sample Discount Applied");
        return discount;
    }

    @Override
    public DiscountApplication getDiscountById(Long discountId) {
        // Mocked discount, replace with repository call
        DiscountApplication discount = new DiscountApplication();
        discount.setId(discountId);
        discount.setCartId(101L);
        discount.setDiscountAmount(30.0);
        discount.setDescription("Discount fetched by ID");
        return discount;
    }

    @Override
    public List<DiscountApplication> getDiscountsForCart(Long cartId) {
        // Example: returning multiple discounts
        List<DiscountApplication> discounts = new ArrayList<>();
        DiscountApplication d1 = new DiscountApplication();
        d1.setId(1L);
        d1.setCartId(cartId);
        d1.setDiscountAmount(20.0);
        d1.setDescription("First discount");
        
        DiscountApplication d2 = new DiscountApplication();
        d2.setId(2L);
        d2.setCartId(cartId);
        d2.setDiscountAmount(15.0);
        d2.setDescription("Second discount");

        discounts.add(d1);
        discounts.add(d2);
        return discounts;
    }
}
