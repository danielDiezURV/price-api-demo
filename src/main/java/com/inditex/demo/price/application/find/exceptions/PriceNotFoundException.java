package com.inditex.demo.price.application.find.exceptions;


public final class PriceNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public PriceNotFoundException(String msg) {
        super(msg);
    }
}
