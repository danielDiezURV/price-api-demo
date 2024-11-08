package com.inditex.demo.shared.infrastructure;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiResponse<ApiError>> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        String parameterName = ex.getName();
        String parameterType = Optional.ofNullable(ex.getRequiredType()).map(Class::getSimpleName).orElse("unknown");
        String message = String.format("Parameter '%s' should be of type '%s'", parameterName, parameterType);

        ApiError error = ApiError.builder()
                                 .title("Invalid Parameter Type")
                                 .detail(message)
                                 .url("http://localhost:8080/prices/")
                                 .build();
        return ResponseEntityHandler.generateError(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApiResponse<ApiError>> handleMissingServletRequestParameterException(MissingServletRequestParameterException ex) {
        String parameterName = ex.getParameterName();
        String message = String.format("Missing required parameter: '"+parameterName+"'");

        ApiError error = ApiError.builder()
                                 .title("Missing Required Parameter")
                                 .detail(message)
                                 .url("http://localhost:8080/prices/")
                                 .build();
        return ResponseEntityHandler.generateError(error, HttpStatus.BAD_REQUEST);
    }

}