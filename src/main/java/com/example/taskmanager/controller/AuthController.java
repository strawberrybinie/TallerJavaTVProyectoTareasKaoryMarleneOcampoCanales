package com.example.taskmanager.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import com.example.taskmanager.dto.AuthRequest;
import com.example.taskmanager.dto.AuthResponse;
import com.example.taskmanager.repository.UserRepository;
import com.example.taskmanager.model.User;
import com.example.taskmanager.security.JwtUtil;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthenticationManager authManager;
    private final UserRepository userRepo;
    private final JwtUtil jwtUtil;

    public AuthController(AuthenticationManager authManager, UserRepository userRepo, JwtUtil jwtUtil){
        this.authManager = authManager;
        this.userRepo = userRepo;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest req){
        authManager.authenticate(new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword()));
        User u = userRepo.findByUsername(req.getUsername()).orElseThrow();
        String token = jwtUtil.generateToken(u);
        return new AuthResponse(token);
    }
}
