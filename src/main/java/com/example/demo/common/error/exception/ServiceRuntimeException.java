package com.example.demo.common.error.exception;

import com.example.demo.common.error.ErrorCode;

public class ServiceRuntimeException extends RuntimeException {

    private ErrorCode errorCode;

    public ServiceRuntimeException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public ServiceRuntimeException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
