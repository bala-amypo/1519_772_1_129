package com.example.demo.service.impl;

import com.example.demo.model.BundleRule;
import com.example.demo.repository.BundleRuleRepository;
import com.example.demo.service.BundleRuleService;
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

        if (bundleRule.getDiscountPercentage() < 0 || bundleRule.getDiscountPercentage() > 100) {
            throw new IllegalArgumentException("Invalid discount");
        }

        bundleRuleRepository.findByRuleName(bundleRule.getRuleName())
                .ifPresent(r -> {
                    throw new IllegalArgumentException("Rule already exists");
                });

        return bundleRuleRepository.save(bundleRule);
    }

    @Override
    public BundleRule updateRule(Long id, BundleRule bundleRule) {

        BundleRule existing = getRuleById(id);

        if (bundleRule.getDiscountPercentage() < 0 || bundleRule.getDiscountPercentage() > 100) {
            throw new IllegalArgumentException("Invalid discount");
        }

        existing.setRuleName(bundleRule.getRuleName());
        existing.setRequiredProductIds(bundleRule.getRequiredProductIds());
        existing.setDiscountPercentage(bundleRule.getDiscountPercentage());
        existing.setActive(bundleRule.getActive());

        return bundleRuleRepository.save(existing);
    }

    @Override
    public BundleRule getRuleById(Long id) {
        return bundleRuleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rule not found"));
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
}
