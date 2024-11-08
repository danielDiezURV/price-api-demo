package com.inditex.demo.price.infrastructure.rest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.inditex.demo.price.application.find.exceptions.PriceNotFoundException;
import com.inditex.demo.shared.infrastructure.ApiError;
import com.inditex.demo.shared.infrastructure.ApiResponse;
import com.inditex.demo.shared.infrastructure.ResponseEntityHandler;


@ControllerAdvice
public class PriceExceptionHandler {

    @ExceptionHandler(PriceNotFoundException.class)
    public ResponseEntity<ApiResponse<ApiError>> handlePriceNotFoundException(PriceNotFoundException ex) {
        ApiError error = ApiError.builder()
                                 .title("Price not found")
                                 .detail(ex.getMessage())
                                 .url("http://localhost:8080/prices/")
                                 .build();
        return ResponseEntityHandler.generateError(error, HttpStatus.NOT_FOUND);
    }
}