package com.userservice.dto;

import com.userservice.entity.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateUserDto(@NotBlank String name,
                            @NotBlank @Email String email,
                            @NotBlank String password,
                            @NotNull Role role) {
}
