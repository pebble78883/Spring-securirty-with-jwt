package com.example.demo.common.error.exception;

import com.example.demo.common.error.ErrorCode;

public class InvalidValueException extends ServiceRuntimeException {

    public InvalidValueException(String message) {
        super(message, ErrorCode.INVALID_TYPE_VALUE);
    }

    public InvalidValueException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

}
