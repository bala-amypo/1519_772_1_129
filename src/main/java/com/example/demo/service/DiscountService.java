package com.example.demo.service;

import com.example.demo.model.DiscountApplication;

import java.util.List;

public interface DiscountService {

    List<DiscountApplication> evaluateDiscounts(Long cartId);

    DiscountApplication getById(Long id);

    List<DiscountApplication> getByCartId(Long cartId);
}
