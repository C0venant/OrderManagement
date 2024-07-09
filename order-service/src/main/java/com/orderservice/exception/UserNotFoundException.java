package com.orderservice.exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(Long id) {
        super("user with id " + id + " not found");
    }
}
