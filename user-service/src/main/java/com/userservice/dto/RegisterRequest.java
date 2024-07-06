package com.userservice.dto;

public record RegisterRequest(String name,
                              String email,
                              String password) {
}
