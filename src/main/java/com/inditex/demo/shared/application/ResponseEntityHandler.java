package com.inditex.demo.shared.application;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public final class ResponseEntityHandler<T> {
    
    private ResponseEntityHandler() {}

    public static <T> ResponseEntity<ApiResponse<T>> generate(ApiResponse<T> response, HttpStatus status) {
        return new ResponseEntity<>(response, status);
    }

    public static <T> ResponseEntity<ApiResponse<T>> generateError(ApiError Error, HttpStatus status) {
        ApiResponse<T> response = new ApiResponse<>(null, null, Error);

        return generate(response, status);
    }

}
