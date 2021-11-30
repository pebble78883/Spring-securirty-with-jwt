package com.example.demo.jwt;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import java.security.Key;

@Getter
@ConstructorBinding
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {

    private final String header;

    private final Key secret;

    private final String issuer;

    private final Long expiryMilliSeconds;

    public JwtProperties(String header, String secret, String issuer, Long expirySeconds) {
        this.header = header;
        this.secret = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
        this.issuer = issuer;
        this.expiryMilliSeconds = expirySeconds * 1000;
    }

}
