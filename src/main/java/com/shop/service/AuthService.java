package com.shop.service;

import com.shop.dto.LoginRequest;
import com.shop.dto.RegisterRequest;
import com.shop.entity.User;

public interface AuthService {
    User register(RegisterRequest request);
    String login(LoginRequest request);
} 