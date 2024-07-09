package com.userservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record AuthenticateUserDto(@NotBlank @Email String email, @NotBlank String password) {
}
