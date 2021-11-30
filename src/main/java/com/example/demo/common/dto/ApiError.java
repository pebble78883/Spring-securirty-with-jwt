package com.example.demo.common.dto;

import com.example.demo.common.error.ErrorCode;
import lombok.Getter;

@Getter
public class ApiError {

    private final String code;

    private final String message;

    public ApiError(final ErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }

    public static ApiError of(final ErrorCode code) {
        return new ApiError(code);
    }

}
