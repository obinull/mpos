package dev.byto.mpos.controller;

import dev.byto.mpos.dto.ApiResponse;
import dev.byto.mpos.dto.AuthRequest;
import dev.byto.mpos.dto.AuthResponse;
import dev.byto.mpos.dto.RegisterRequest;
import dev.byto.mpos.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AuthResponse>> register(@RequestBody RegisterRequest request) {
        AuthResponse authResponse = authService.register(request);
        ApiResponse<AuthResponse> response = new ApiResponse<>("SUCCESS", "User registered successfully", authResponse);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@RequestBody AuthRequest request) {
        AuthResponse authResponse = authService.login(request);
        ApiResponse<AuthResponse> response = new ApiResponse<>("SUCCESS", "Login successful", authResponse);
        return ResponseEntity.ok(response);
    }
}
