package com.example.demo.common.dto;

import com.example.demo.common.error.ErrorCode;
import lombok.Getter;

@Getter
public class ApiResult<T> {

    private final Boolean success;

    private final T data;

    private final ApiError error;

    private ApiResult(T data) {
        this.success = true;
        this.data = data;
        this.error = null;
    }

    private ApiResult(ErrorCode code) {
        this.success = false;
        this.data = null;
        this.error = ApiError.of(code);
    }

    public static <T> ApiResult<T> ok(T data) {
        return new ApiResult<>(data);
    }

    public static <T> ApiResult<T> fail(ErrorCode errorCode) {
        return new ApiResult<>(errorCode);
    }

}
