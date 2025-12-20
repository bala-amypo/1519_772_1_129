package com.example.demo.service.impl;

import com.example.demo.model.BundleRule;
import com.example.demo.repository.BundleRuleRepository;
import com.example.demo.service.BundleRuleService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service   // 🔴 VERY IMPORTANT (bean creation)
public class BundleRuleServiceImpl implements BundleRuleService {

    private final BundleRuleRepository bundleRuleRepository;

    public BundleRuleServiceImpl(BundleRuleRepository bundleRuleRepository) {
        this.bundleRuleRepository = bundleRuleRepository;
    }

    @Override
    public BundleRule createRule(BundleRule rule) {

        validateRule(rule);

        rule.setActive(true);
        return bundleRuleRepository.save(rule);
    }

    @Override
    public BundleRule updateRule(Long id, BundleRule rule) {

        BundleRule existing = bundleRuleRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Bundle rule not found"));

        validateRule(rule);

        existing.setRuleName(rule.getRuleName());
        existing.setRequiredProductIds(rule.getRequiredProductIds());
        existing.setDiscountPercentage(rule.getDiscountPercentage());

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

    // 🔹 Validation logic required by tests
    private void validateRule(BundleRule rule) {

        if (rule.getRequiredProductIds() == null ||
                rule.getRequiredProductIds().trim().isEmpty()) {
            throw new IllegalArgumentException("Required products cannot be empty");
        }

        if (rule.getDiscountPercentage() == null ||
                rule.getDiscountPercentage() < 0 ||
                rule.getDiscountPercentage() > 100) {
            throw new IllegalArgumentException("Discount must be between 0 and 100");
        }
    }
}
