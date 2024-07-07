package com.userservice.dto;

import java.time.LocalDateTime;

public record ErrorDto(String message, LocalDateTime timestamp) {

    public static ErrorDto of(String message) {
        return new ErrorDto(message, LocalDateTime.now());
    }
}
