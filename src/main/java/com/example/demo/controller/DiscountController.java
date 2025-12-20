package com.example.demo.controller;

import com.example.demo.model.DiscountApplication;
import com.example.demo.service.DiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/discounts")
public class DiscountController {

    @Autowired
    private DiscountService discountService;

    // POST /evaluate/{cartId} - Evaluate discounts
    @PostMapping("/evaluate/{cartId}")
    public ResponseEntity<DiscountApplication> evaluateDiscount(@PathVariable Long cartId) {
        DiscountApplication discount = discountService.evaluateDiscount(cartId);
        return ResponseEntity.ok(discount);
    }

    // GET /{id} - Get discount application by id
    @GetMapping("/{id}")
    public ResponseEntity<DiscountApplication> getDiscountById(@PathVariable Long id) {
        DiscountApplication discount = discountService.getDiscountById(id);
        return ResponseEntity.ok(discount);
    }

    // GET /cart/{cartId} - Get discounts for cart
    @GetMapping("/cart/{cartId}")
    public ResponseEntity<List<DiscountApplication>> getDiscountsForCart(@PathVariable Long cartId) {
        List<DiscountApplication> discounts = discountService.getDiscountsForCart(cartId);
        return ResponseEntity.ok(discounts);
    }
}
