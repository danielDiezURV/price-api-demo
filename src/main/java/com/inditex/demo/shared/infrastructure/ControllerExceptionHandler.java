package com.inditex.demo.shared.infrastructure;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import com.inditex.demo.price.application.find.exceptions.ExceptionControllerPriceNotFoundException;
import com.inditex.demo.price.application.find.exceptions.ExceptionControllerPriceParamException;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(ExceptionControllerPriceParamException.class)
    public ResponseEntity<Object> handlePriceParamException(ExceptionControllerPriceParamException ex, WebRequest request) {
        return new ResponseEntity<>(this.generateApiError("Price null param Exception", ex.getMessage(), request), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ExceptionControllerPriceNotFoundException.class)
    public ResponseEntity<Object> handlePriceNotFoundException(ExceptionControllerPriceNotFoundException ex, WebRequest request) {
        return new ResponseEntity<>(this.generateApiError("Price Not found Exception", ex.getMessage(), request), HttpStatus.NOT_FOUND);
    }


    private ApiError generateApiError(String title, String detail, WebRequest request) {
        return ApiError.builder()
                       .title(title)
                       .detail(detail)
                       .url(((ServletWebRequest)request).getRequest().getRequestURI())
                       .build();
    }
}