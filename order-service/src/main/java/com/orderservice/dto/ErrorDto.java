package com.orderservice.dto;

import java.time.LocalDateTime;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;

public record ErrorDto(
        String message,
        LocalDateTime timestamp,
        @JsonInclude(JsonInclude.Include.NON_NULL) Map<String, String> validationResult
) {

    public static ErrorDto of(String message) {
        return new ErrorDto(message, LocalDateTime.now(), null);
    }

    public static ErrorDto of(String message, Map<String, String> validationResult) {
        return new ErrorDto(message, LocalDateTime.now(), validationResult);
    }
}
