package com.orderservice.exception;

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(Long id, Long userId) {
        super("order not found with id " + id + " and user id " + userId);
    }
}
