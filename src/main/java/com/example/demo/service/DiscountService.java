package com.example.demo.service;

import com.example.demo.model.DiscountApplication;

import java.util.List;

public interface DiscountService {

    // Evaluate all applicable discounts for a cart
    List<DiscountApplication> evaluateDiscounts(Long cartId);

    // Get all discount applications for a cart
    List<DiscountApplication> getApplicationsForCart(Long cartId);

    // Get single discount application by id
    DiscountApplication getApplicationById(Long id);
}
