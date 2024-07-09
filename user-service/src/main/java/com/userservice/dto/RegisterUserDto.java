package com.userservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RegisterUserDto(@NotBlank String name,
                              @NotBlank @Email String email,
                              @NotBlank String password) {
}
