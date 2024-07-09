package com.userservice.dto;

import com.userservice.entity.Role;
import com.userservice.entity.User;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserDto(
        @NotNull Long id,
        @NotBlank String name,
        @NotBlank @Email String email,
        @NotNull Role role
) {

    public static UserDto of(User user) {
        return new UserDto(user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole());
    }
}
