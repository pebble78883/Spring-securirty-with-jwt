package com.example.demo.member.exception;

import com.example.demo.common.error.ErrorCode;
import com.example.demo.common.error.exception.InvalidValueException;

public class EmailDuplicationException extends InvalidValueException {

    public EmailDuplicationException(String email) {
        super(email + " is already exists", ErrorCode.EMAIL_DUPLICATION);
    }

}
