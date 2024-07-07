package com.userservice.dto;

import jakarta.validation.constraints.NotBlank;

public record UpdateUserDto(@NotBlank String name,
                            @NotBlank String email) {
}
