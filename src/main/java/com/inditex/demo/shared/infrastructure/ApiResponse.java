package com.inditex.demo.shared.infrastructure;

import lombok.Getter;

@Getter
public class ApiResponse<T> {
    private final T data;
    private final String info;
    private final ApiError error;

    public ApiResponse(T data, String info, ApiError error) {
        this.data = data;
        this.info = info;
        this.error = error;
    }
}
