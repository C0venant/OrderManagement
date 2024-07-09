package com.orderservice.dto;

import jakarta.validation.constraints.NotNull;

public record UpdateOrderDto(@NotNull Long id, @NotNull String product, @NotNull Integer quantity) {
}
