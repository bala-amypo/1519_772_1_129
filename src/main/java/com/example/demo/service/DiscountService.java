package com.example.demo.service;

import com.example.demo.model.DiscountApplication;

import java.util.List;

public interface DiscountService {

    
    List<DiscountApplication> evaluateDiscounts(Long cartId);

  
    List<DiscountApplication> getApplicationsForCart(Long cartId);

    DiscountApplication getApplicationById(Long id);
}
