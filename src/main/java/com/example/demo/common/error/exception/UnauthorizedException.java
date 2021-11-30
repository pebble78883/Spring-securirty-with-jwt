package com.example.demo.common.error.exception;

import com.example.demo.common.error.ErrorCode;

public class UnauthorizedException extends ServiceRuntimeException {

    public UnauthorizedException(String message) {
        super(message, ErrorCode.UNAUTHORIZED);
    }

}
