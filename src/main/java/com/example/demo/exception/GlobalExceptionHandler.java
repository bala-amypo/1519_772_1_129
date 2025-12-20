package com.example.demo.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // ---------- PRODUCT ----------
    @ExceptionHandler(ProductException.class)
    public ResponseEntity<Map<String, Object>> handleProduct(ProductException ex) {
        return build(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    // ---------- BUNDLE RULE ----------
    @ExceptionHandler(BundleRuleException.class)
    public ResponseEntity<Map<String, Object>> handleBundle(BundleRuleException ex) {
        return build(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    // ---------- CART ----------
    @ExceptionHandler(CartException.class)
    public ResponseEntity<Map<String, Object>> handleCart(CartException ex) {
        return build(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    // ---------- CART ITEM ----------
    @ExceptionHandler(CartItemException.class)
    public ResponseEntity<Map<String, Object>> handleCartItem(CartItemException ex) {
        return build(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    // ---------- DISCOUNT ----------
    @ExceptionHandler(DiscountException.class)
    public ResponseEntity<Map<String, Object>> handleDiscount(DiscountException ex) {
        return build(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    // ---------- ENTITY NOT FOUND ----------
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFound(EntityNotFoundException ex) {
        return build(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    // ---------- FALLBACK ----------
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleOther(Exception ex) {
        return build(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");
    }

    private ResponseEntity<Map<String, Object>> build(HttpStatus status, String message) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("error", message);
        return new ResponseEntity<>(body, status);
    }
}
