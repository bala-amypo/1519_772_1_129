package com.example.demo.controller;

import com.example.demo.model.BundleRule;
import com.example.demo.service.BundleRuleService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bundle-rules")
public class BundleRuleController {

    private final BundleRuleService bundleRuleService;

    public BundleRuleController(BundleRuleService bundleRuleService) {
        this.bundleRuleService = bundleRuleService;
    }

    @PostMapping
    public BundleRule createRule(@RequestBody BundleRule rule) {
        return bundleRuleService.createRule(rule);
    }
}
