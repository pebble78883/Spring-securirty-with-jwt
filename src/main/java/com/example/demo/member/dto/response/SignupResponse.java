package com.example.demo.member.dto.response;

import lombok.Getter;

@Getter
public class SignupResponse {

    private final String memberName;

    public SignupResponse(final String memberName) {
        this.memberName = memberName;
    }

}
