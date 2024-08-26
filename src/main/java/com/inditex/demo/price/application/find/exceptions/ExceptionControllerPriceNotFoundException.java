package com.inditex.demo.price.application.find.exceptions;


public final class ExceptionControllerPriceNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ExceptionControllerPriceNotFoundException(String msg) {
        super(msg);
    }
}
