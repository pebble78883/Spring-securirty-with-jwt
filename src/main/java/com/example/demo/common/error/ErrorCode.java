package com.example.demo.common.error;

import lombok.Getter;

@Getter
public enum ErrorCode {

    // Common
    INTERNAL_SERVER_ERROR(500, "C001", "Server error"),
    ENTITY_NOT_FOUND(404, "C002", " Entity not Found"),
    INVALID_PARAMETER(400, "C003", " Invalid parameters"),
    INVALID_TYPE_VALUE(400, "C004", " Invalid Type Value"),

    // Auth
    UNAUTHORIZED(401, "A001", "Authorization failed"),
    ACCESS_DENIED(403, "A002", "Access denied"),

    // Member
    EMAIL_DUPLICATION(400, "M001", "Email is duplication");

    private final int status;

    private final String code;

    private final String message;

    ErrorCode(int status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    @Override
    public String toString() {
        return super.toString();
    }

}
