package com.userservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.userservice.dto.UpdateUserDto;
import com.userservice.dto.UserDto;
import com.userservice.service.UserService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@SecurityRequirement(name = "bearerAuth")
public class UserController {

    private final UserService userService;

    @PutMapping("/me")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UpdateUserDto updateUserDto) {
        return ResponseEntity.ok(userService.updateUser(updateUserDto));
    }

    @GetMapping("/me")
    public ResponseEntity<UserDto> getUserCurrent() {
        return ResponseEntity.ok(userService.getCurrentUser());
    }
}
