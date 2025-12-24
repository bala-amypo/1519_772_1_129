package com.example.demo.service;

import com.example.demo.model.BundleRule;

import java.util.List;

public interface BundleRuleService {

    BundleRule createRule(BundleRule bundleRule);

    BundleRule updateRule(Long id, BundleRule bundleRule);

    BundleRule getRuleById(Long id);

    List<BundleRule> getActiveRules();

    void deactivateRule(Long id);
}
