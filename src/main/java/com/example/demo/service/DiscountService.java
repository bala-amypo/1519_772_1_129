package com.example.demo.service;

import com.example.demo.model.DiscountApplication;
import com.example.demo.model.CartItem;

import java.util.List;

public interface DiscountService {

    // Evaluate discount for a specific cart
    DiscountApplication evaluateDiscount(Long cartId);

    // Get discount by its id
    DiscountApplication getDiscountById(Long discountId);

    // Get all discounts applied to a specific cart
    List<DiscountApplication> getDiscountsForCart(Long cartId);
}
