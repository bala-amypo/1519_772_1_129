package com.example.demo.service.impl;

import com.example.demo.model.BundleRule;
import com.example.demo.repository.BundleRuleRepository;
import com.example.demo.service.BundleRuleService;
import org.springframework.stereotype.Service;

@Service
public class BundleRuleServiceImpl implements BundleRuleService {

    private final BundleRuleRepository bundleRuleRepository;

    public BundleRuleServiceImpl(BundleRuleRepository bundleRuleRepository) {
        this.bundleRuleRepository = bundleRuleRepository;
    }

    

    @Override
    public BundleRule createRule(BundleRule rule) {
        Double discount = rule.getDiscountPercentage();
        if (discount == null || discount < 0 || discount > 100) {
            throw new IllegalArgumentException(
                    "Discount percentage must be between 0 and 100");
        }

        String required = rule.getRequiredProductIds();
        if (required == null || required.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "requiredProductIds cannot be empty");
        }

        return bundleRuleRepository.save(rule);
    }


    private void validateRule(BundleRule rule) {
        // discountPercentage must be between 0 and 100
        Double discount = rule.getDiscountPercentage();
        if (discount == null || discount < 0 || discount > 100) {
            // message must contain "between 0 and 100"
            throw new IllegalArgumentException(
                    "Discount percentage must be between 0 and 100");
        }

        // requiredProductIds cannot be empty or blank
        String required = rule.getRequiredProductIds();
        if (required == null || required.trim().isEmpty()) {
            // message must contain "cannot be empty"
            throw new IllegalArgumentException(
                    "requiredProductIds cannot be empty");
        }
    }
}
