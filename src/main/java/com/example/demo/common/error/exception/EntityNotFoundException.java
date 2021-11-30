package com.example.demo.common.error.exception;

import com.example.demo.common.error.ErrorCode;

public class EntityNotFoundException extends ServiceRuntimeException {

    public EntityNotFoundException(String message) {
        super(message, ErrorCode.ENTITY_NOT_FOUND);
    }

}
