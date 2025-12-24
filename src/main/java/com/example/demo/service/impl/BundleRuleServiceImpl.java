package com.example.demo.service.impl;

import com.example.demo.model.BundleRule;
import com.example.demo.repository.BundleRuleRepository;
import com.example.demo.service.BundleRuleService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BundleRuleServiceImpl implements BundleRuleService {

    private final BundleRuleRepository bundleRuleRepository;

    public BundleRuleServiceImpl(BundleRuleRepository bundleRuleRepository) {
        this.bundleRuleRepository = bundleRuleRepository;
    }

    @Override
    public BundleRule createRule(BundleRule bundleRule) {
        validateRule(bundleRule);
        return bundleRuleRepository.save(bundleRule);
    }

    @Override
    public BundleRule updateRule(Long id, BundleRule updated) {
        BundleRule existing = getRuleById(id);
        existing.setRuleName(updated.getRuleName());
        existing.setRequiredProductIds(updated.getRequiredProductIds());
        existing.setDiscountPercentage(updated.getDiscountPercentage());
        existing.setActive(updated.getActive());
        validateRule(existing);
        return bundleRuleRepository.save(existing);
    }

    @Override
    public BundleRule getRuleById(Long id) {
        return bundleRuleRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Bundle rule not found"));
    }

    @Override
    public List<BundleRule> getActiveRules() {
        return bundleRuleRepository.findByActiveTrue();
    }

    @Override
    public void deactivateRule(Long id) {
        BundleRule rule = getRuleById(id);
        rule.setActive(false);
        bundleRuleRepository.save(rule);
    }

    private void validateRule(BundleRule rule) {
        // discountPercentage must be between 0 and 100
        Double discount = rule.getDiscountPercentage();
        if (discount == null || discount < 0 || discount > 100) {
            // message must contain "between 0 and 100"
            throw new IllegalArgumentException(
                    "Discount percentage must be between 0 and 100");
        }

        // requiredProductIds cannot be empty / blank
        String required = rule.getRequiredProductIds();
        if (required == null || required.trim().isEmpty()) {
            // message must contain "cannot be empty"
            throw new IllegalArgumentException(
                    "requiredProductIds cannot be empty");
        }
    }
}
