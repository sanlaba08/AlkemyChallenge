package com.alkemy.challenge.controller;

import com.alkemy.challenge.dto.ErrorResponseDto;
import com.alkemy.challenge.exceptions.OperationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ControllerAdvice extends ResponseEntityExceptionHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(OperationException.class)
    public ErrorResponseDto handleEmployeeException(OperationException e) {
        return new ErrorResponseDto(5, e.getMessage());
    }
}
