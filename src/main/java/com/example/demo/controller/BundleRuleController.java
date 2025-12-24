package com.example.demo.controller;

import com.example.demo.model.BundleRule;
import com.example.demo.service.BundleRuleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bundles")
public class BundleRuleController {

    private final BundleRuleService bundleRuleService;

    public BundleRuleController(BundleRuleService bundleRuleService) {
        this.bundleRuleService = bundleRuleService;
    }

    @PostMapping
    public BundleRule create(@RequestBody BundleRule bundleRule) {
        return bundleRuleService.createRule(bundleRule);
    }

    @PutMapping("/{id}")
    public BundleRule update(@PathVariable Long id, @RequestBody BundleRule bundleRule) {
        return bundleRuleService.updateRule(id, bundleRule);
    }

    @GetMapping("/{id}")
    public BundleRule getById(@PathVariable Long id) {
        return bundleRuleService.getRuleById(id);
    }

    @GetMapping("/active")
    public List<BundleRule> getActive() {
        return bundleRuleService.getActiveRules();
    }

    @DeleteMapping("/{id}")
    public void deactivate(@PathVariable Long id) {
        bundleRuleService.deactivateRule(id);
    }
}
