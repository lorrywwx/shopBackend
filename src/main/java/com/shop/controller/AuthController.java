package com.shop.controller;

import com.shop.dto.ApiResponse;
import com.shop.dto.LoginRequest;
import com.shop.dto.RegisterRequest;
import com.shop.entity.User;
import com.shop.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ApiResponse<User> register(@RequestBody RegisterRequest request) {
        try {
            User user = authService.register(request);
            return ApiResponse.success("注册成功", user);
        } catch (Exception e) {
            return ApiResponse.error(400, e.getMessage());
        }
    }

    @PostMapping("/login")
    public ApiResponse<Map<String, Object>> login(@RequestBody LoginRequest request) {
        try {
            String token = authService.login(request);
            Map<String, Object> data = new HashMap<>();
            data.put("token", token);
            return ApiResponse.success("登录成功", data);
        } catch (Exception e) {
            return ApiResponse.error(400, e.getMessage());
        }
    }
} 