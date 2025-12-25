package com.example.demo.repository;

import com.example.demo.model.BundleRule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BundleRuleRepository extends JpaRepository<BundleRule, Long> {

    Optional<BundleRule> findByRuleName(String ruleName);

    List<BundleRule> findByActiveTrue();
}
