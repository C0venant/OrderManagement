package com.orderservice.dto;

import com.orderservice.entity.Order;

public record OrderDto(Long id, Long userId, String product, Integer quantity, Order.Status status) {

    public static OrderDto of(Order order) {
        return new OrderDto(order.getId(),
                order.getUser().getId(),
                order.getProduct(),
                order.getQuantity(),
                order.getStatus());
    }

}
