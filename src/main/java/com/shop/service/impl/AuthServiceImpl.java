package com.shop.service.impl;

import com.shop.dto.LoginRequest;
import com.shop.dto.RegisterRequest;
import com.shop.entity.User;
import com.shop.mapper.UserMapper;
import com.shop.service.AuthService;
import com.shop.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public User register(RegisterRequest request) {
        // 检查用户名是否已存在
        if (userMapper.findByUsername(request.getUsername()) != null) {
            throw new RuntimeException("用户名已存在");
        }

        // 检查邮箱是否已存在
        if (userMapper.findByEmail(request.getEmail()) != null) {
            throw new RuntimeException("邮箱已被注册");
        }

        // 创建新用户
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());

        // 保存用户
        userMapper.insert(user);
        return user;
    }

    @Override
    public String login(LoginRequest request) {
        // 查找用户
        User user = userMapper.findByUsername(request.getUsername());
        if (user == null) {
            throw new RuntimeException("用户名或密码错误");
        }

        // 验证密码
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("用户名或密码错误");
        }

        // 生成token
        return jwtUtil.generateToken(user.getUsername());
    }
} 