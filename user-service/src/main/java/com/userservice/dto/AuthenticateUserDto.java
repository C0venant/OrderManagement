package com.userservice.dto;

import jakarta.validation.constraints.NotBlank;

public record AuthenticateUserDto(@NotBlank String email, @NotBlank String password) {
}
