package com.orderservice.dto;

import com.orderservice.entity.Order;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record OrderDto(Long id, Long userId, @NotBlank String product, @NotNull Integer quantity, Order.Status status) {

    public static OrderDto of(Order order) {
        return new OrderDto(order.getId(),
                order.getUser().getId(),
                order.getProduct(),
                order.getQuantity(),
                order.getStatus());
    }
}
