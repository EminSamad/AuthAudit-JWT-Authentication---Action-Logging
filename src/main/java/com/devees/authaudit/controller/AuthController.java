package com.devees.authaudit.controller;

import com.devees.authaudit.config.JwtUtils;
import com.devees.authaudit.dto.JwtResponseDto;
import com.devees.authaudit.dto.LoginRequestDto;
import com.devees.authaudit.dto.RegisterRequestDto;
import com.devees.authaudit.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtUtils jwtUtils;

    @PostMapping("/register")
    public String register(@Valid @RequestBody RegisterRequestDto request) {
        userService.register(request);
        return "User registered successfully!";
    }

    @PostMapping("/login")
    public JwtResponseDto login(@Valid @RequestBody LoginRequestDto request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtUtils.generateToken(request.getUsername());
        return new JwtResponseDto(token, request.getUsername());
    }
}
