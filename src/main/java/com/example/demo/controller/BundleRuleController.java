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

  
    @PostMapping
    public BundleRule createRule(@RequestBody BundleRule rule) {
        return bundleRuleService.createRule(rule);
    }

   
    @PutMapping("/{id}")
    public BundleRule updateRule(@PathVariable Long id,
                                 @RequestBody BundleRule rule) {
        return bundleRuleService.updateRule(id, rule);
    }

    @GetMapping("/{id}")
    public BundleRule getRule(@PathVariable Long id) {
        return bundleRuleService.getRule(id);
    }


    @GetMapping("/active")
    public List<BundleRule> getActiveRules() {
        return bundleRuleService.getActiveRules();
    }

  
    @PutMapping("/{id}/deactivate")
    public void deactivateRule(@PathVariable Long id) {
        bundleRuleService.deactivateRule(id);
    }
}
