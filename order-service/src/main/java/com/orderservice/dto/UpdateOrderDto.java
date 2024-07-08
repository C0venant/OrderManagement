package com.orderservice.dto;

public record UpdateOrderDto(Long id, String product, Integer quantity) {
}
