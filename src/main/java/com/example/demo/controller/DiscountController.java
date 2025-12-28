package com.example.demo.controller;

import com.example.demo.model.DiscountApplication;
import com.example.demo.service.DiscountService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/discounts")
@Tag(name = "Discounts")
public class DiscountController {

    private final DiscountService discountService;

    public DiscountController(DiscountService discountService) {
        this.discountService = discountService;
    }

    @PostMapping("/evaluate/{cartId}")
    public ResponseEntity<List<DiscountApplication>> evaluate(
            @PathVariable Long cartId) {
        List<DiscountApplication> apps = discountService.evaluateDiscounts(cartId);
        return ResponseEntity.ok(apps);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DiscountApplication> getById(@PathVariable Long id) {
        DiscountApplication app = discountService.getApplicationById(id);
        return ResponseEntity.ok(app);
    }

    @GetMapping("/cart/{cartId}")
    public ResponseEntity<List<DiscountApplication>> getForCart(
            @PathVariable Long cartId) {
        return ResponseEntity.ok(discountService.getApplicationsForCart(cartId));
    }
}
