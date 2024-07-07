package com.userservice.dto;

import com.userservice.entity.Role;
import com.userservice.entity.User;

public record UserDto(Long id, String name, String email, Role role) {

    public static UserDto of(User user) {
        return new UserDto(user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole());
    }
}
