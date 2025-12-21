package com.example.demo.controller;

import com.example.demo.model.DiscountApplication;
import com.example.demo.service.DiscountService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/discounts")
public class DiscountController {

    private final DiscountService discountService;

    public DiscountController(DiscountService discountService) {
        this.discountService = discountService;
    }

    // POST /api/discounts/evaluate/{cartId}
    @PostMapping("/evaluate/{cartId}")
    public List<DiscountApplication> evaluate(@PathVariable Long cartId) {
        return discountService.evaluateDiscounts(cartId);
    }

    // GET /api/discounts/{id}
    @GetMapping("/{id}")
    public DiscountApplication getById(@PathVariable Long id) {
        return discountService.getById(id);
    }

    // GET /api/discounts/cart/{cartId}
    @GetMapping("/cart/{cartId}")
    public List<DiscountApplication> getByCart(@PathVariable Long cartId) {
        return discountService.getByCartId(cartId);
    }
}
