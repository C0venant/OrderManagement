package com.userservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.userservice.dto.ErrorDto;
import com.userservice.exception.EmailAlreadyInUseException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(EmailAlreadyInUseException.class)
    public ErrorDto handleEmailAlreadyInUseException(EmailAlreadyInUseException ex) {
        return ErrorDto.of(ex.getMessage());
    }
}
