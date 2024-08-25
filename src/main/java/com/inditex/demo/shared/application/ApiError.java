package com.inditex.demo.shared.application;

import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class ApiError{
    
    private final String title;

    private final String detail;

    private final String url;

    public ApiError(String title, String detail, String url) {
        this.title = title;
        this.detail = detail;
        this.url = url;
    }

}