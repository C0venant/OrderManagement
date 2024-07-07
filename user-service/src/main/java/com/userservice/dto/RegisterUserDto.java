package com.userservice.dto;

import jakarta.validation.constraints.NotBlank;

public record RegisterUserDto(@NotBlank String name,
                              @NotBlank String email,
                              @NotBlank String password) {
}
