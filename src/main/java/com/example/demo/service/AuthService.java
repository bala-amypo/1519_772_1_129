package com.example.demo.service;

import com.example.demo.controller.dto.AuthRequest;
import com.example.demo.controller.dto.AuthResponse;

public interface AuthService {

    void register(String email, String password, String role);

    AuthResponse login(AuthRequest request);
}
