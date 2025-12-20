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

    // =========================
    // PRODUCT
    // =========================
    @ExceptionHandler(ProductException.class)
    public ResponseEntity<Map<String, Object>> handleProduct(ProductException ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    // =========================
    // BUNDLE RULE
    // =========================
    @ExceptionHandler(BundleRuleException.class)
    public ResponseEntity<Map<String, Object>> handleBundle(BundleRuleException ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    // =========================
    // CART
    // =========================
    @ExceptionHandler(CartException.class)
    public ResponseEntity<Map<String, Object>> handleCart(CartException ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    // =========================
    // CART ITEM
    // =========================
    @ExceptionHandler(CartItemException.class)
    public ResponseEntity<Map<String, Object>> handleCartItem(CartItemException ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    // =========================
    // DISCOUNT
    // =========================
    @ExceptionHandler(DiscountException.class)
    public ResponseEntity<Map<String, Object>> handleDiscount(DiscountException ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    // =========================
    // ENTITY NOT FOUND (JPA)
    // =========================
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFound(EntityNotFoundException ex) {
        return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    // =========================
    // GENERIC EXCEPTION (500)
    // =========================
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleAll(Exception ex) {
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");
    }

    // =========================
    // RESPONSE BUILDER
    // =========================
    private ResponseEntity<Map<String, Object>> buildResponse(HttpStatus status, String message) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("error", message);
        return new ResponseEntity<>(body, status);
    }

    // =====================================================
    // ALL CUSTOM EXCEPTIONS — INSIDE SAME FILE
    // =====================================================

    public static class ProductException extends RuntimeException {
        public ProductException(String message) {
            super(message);
        }
    }

    public static class BundleRuleException extends RuntimeException {
        public BundleRuleException(String message) {
            super(message);
        }
    }

    public static class CartException extends RuntimeException {
        public CartException(String message) {
            super(message);
        }
    }

    public static class CartItemException extends RuntimeException {
        public CartItemException(String message) {
            super(message);
        }
    }

    public static class DiscountException extends RuntimeException {
        public DiscountException(String message) {
            super(message);
        }
    }
}
