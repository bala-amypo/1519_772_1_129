package com.example.demo.security;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class JwtTokenProvider {

    private static final String SECRET_KEY = "bundle-save-secret-key-123456";

    public String generateToken(String email, String role, Long userId) {
        // Very simple pseudo-token: base64 of email|role|userId|secret
        String raw = email + "|" + role + "|" + userId + "|" + SECRET_KEY;
        return Base64.getEncoder().encodeToString(
                raw.getBytes(StandardCharsets.UTF_8));
    }

    public boolean validateToken(String token) {
        try {
            byte[] decoded = Base64.getDecoder().decode(token);
            String raw = new String(decoded, StandardCharsets.UTF_8);
            // token is valid if it contains the secret key
            return raw.endsWith(SECRET_KEY);
        } catch (IllegalArgumentException ex) {
            return false;
        }
    }
}
