package com.bibliotech.auth_service.controller;

import org.springframework.web.bind.annotation.*;

import com.bibliotech.auth_service.dto.*;
import com.bibliotech.auth_service.service.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest request) {
        return authService.register(request);
    }
    
    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }
    
    @GetMapping("/test")
    public String test() {
        return "JWT authentication successful";
    }
}
