package com.example.demo.jwt;

import lombok.Getter;

@Getter
public class JwtAuthentication {

    private final String token;

    private final String memberName;

    public JwtAuthentication(String token, String memberName) {
        this.token = token;
        this.memberName = memberName;
    }

}
