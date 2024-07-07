package com.userservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.userservice.dto.AuthenticateUserDto;
import com.userservice.dto.RegisterUserDto;
import com.userservice.dto.TokenDto;
import com.userservice.dto.UserDto;
import com.userservice.service.AuthenticationService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<UserDto> registerUser(@Valid @RequestBody RegisterUserDto registerUserDto) {
        return ResponseEntity.ok(authenticationService.register(registerUserDto));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<TokenDto> authenticate(@Valid @RequestBody AuthenticateUserDto authenticateUserDto) {
        return ResponseEntity.ok(authenticationService.authenticate(authenticateUserDto));
    }
}
