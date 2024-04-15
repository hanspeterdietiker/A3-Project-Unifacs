package com.restaurant.a3.RestaurantApi.Exceptions;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorMessage> entityNotFoundException(RuntimeException exception,
                                                                HttpServletRequest request) {

        log.error("Api error!", exception);

        return ResponseEntity.
                status(HttpStatus.NOT_FOUND).
                contentType(MediaType.APPLICATION_JSON).
                body(new ErrorMessage(request, HttpStatus.NOT_FOUND, exception.getMessage()));
    }
}
