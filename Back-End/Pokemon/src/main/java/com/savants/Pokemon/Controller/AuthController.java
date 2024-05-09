package com.savants.Pokemon.Controller;

import com.savants.Pokemon.DTOs.LoginRequest;
import com.savants.Pokemon.DTOs.RegisterRequest;
import com.savants.Pokemon.DTOs.TokenRequest;
import com.savants.Pokemon.Services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping(value = "login")
    public ResponseEntity<TokenRequest> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(userService.login(request));
    }

    @PostMapping(value = "register")
    public ResponseEntity<TokenRequest> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(userService.register(request));
    }
}
