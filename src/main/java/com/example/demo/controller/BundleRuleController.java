package com.example.demo.controller;

import com.example.demo.model.BundleRule;
import com.example.demo.service.BundleRuleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bundle-rules")
public class BundleRuleController {

    private final BundleRuleService bundleRuleService;

    public BundleRuleController(BundleRuleService bundleRuleService) {
        this.bundleRuleService = bundleRuleService;
    }

    // POST /api/bundle-rules
    @PostMapping
    public BundleRule createRule(@RequestBody BundleRule rule) {
        return bundleRuleService.createRule(rule);
    }

    // PUT /api/bundle-rules/{id}
    @PutMapping("/{id}")
    public BundleRule updateRule(@PathVariable Long id,
                                 @RequestBody BundleRule rule) {
        return bundleRuleService.updateRule(id, rule);
    }

    // GET /api/bundle-rules/{id}
    @GetMapping("/{id}")
    public BundleRule getRule(@PathVariable Long id) {
        return bundleRuleService.getRule(id);
    }

    // GET /api/bundle-rules/active
    @GetMapping("/active")
    public List<BundleRule> getActiveRules() {
        return bundleRuleService.getActiveRules();
    }

    // PUT /api/bundle-rules/{id}/deactivate
    @PutMapping("/{id}/deactivate")
    public void deactivateRule(@PathVariable Long id) {
        bundleRuleService.deactivateRule(id);
    }
}
