package com.example.demo.jwt;

import com.example.demo.member.entity.Member;
import com.example.demo.member.exception.MemberNotFoundException;
import com.example.demo.member.service.MemberService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class JwtAuthenticationProvider implements AuthenticationProvider {

    private final JwtProperties jwtProperties;

    private final MemberService memberService;

    public JwtAuthenticationProvider(JwtProperties jwtProperties, MemberService memberService) {
        this.jwtProperties = jwtProperties;
        this.memberService = memberService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String principal = String.valueOf(authentication.getPrincipal());
        String credentials = String.valueOf(authentication.getCredentials());

        try {
            Member member = memberService.login(principal, credentials);

            List<GrantedAuthority> authorities = member.getAuthorities().stream()
                    .map(authority -> new SimpleGrantedAuthority(authority.getAuthorityName()))
                    .collect(Collectors.toList());

            String jwt = generateJwt(principal, authorities);

            JwtAuthenticationToken authenticated = new JwtAuthenticationToken(jwt, null, authorities);
            authenticated.setDetails(member);

            return authenticated;
        } catch (MemberNotFoundException e) {
            throw new UsernameNotFoundException(e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new BadCredentialsException(e.getMessage());
        } catch (DataAccessException e) {
            throw new AuthenticationServiceException(e.getMessage(), e);
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (JwtAuthenticationToken.class.isAssignableFrom(authentication));
    }

    private String generateJwt(String subject, List<GrantedAuthority> authorities) {
        final Date issuedAt = new Date(System.currentTimeMillis());
        final Date expiration = new Date(System.currentTimeMillis() + jwtProperties.getExpiryMilliSeconds());

        final String roles = authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        return Jwts.builder()
                .claim("authorities", roles)
                .setIssuer(jwtProperties.getIssuer())
                .setSubject(subject)
                .setIssuedAt(issuedAt)
                .setExpiration(expiration)
                .signWith(jwtProperties.getSecret(), SignatureAlgorithm.HS512)
                .compact();
    }

}
