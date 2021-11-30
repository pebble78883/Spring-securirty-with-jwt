package com.example.demo.member.exception;

import com.example.demo.common.error.exception.EntityNotFoundException;

public class MemberNotFoundException extends EntityNotFoundException {

    public MemberNotFoundException(String target) {
        super(target + "is not found");
    }

}
