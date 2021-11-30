package com.example.demo.jwt;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@Slf4j
public class JwtAuthenticationFilter extends GenericFilterBean {

    private final JwtProperties jwtProperties;

    public JwtAuthenticationFilter(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            String jwt = getToken(request);
            if (StringUtils.hasText(jwt)) {
                Claims claims = getClaims(jwt);

                if (claims != null) {
                    String memberName = claims.getSubject();

                    Collection<? extends GrantedAuthority> authorities =
                            Arrays.stream(claims.get("authorities").toString().split(","))
                                    .map(SimpleGrantedAuthority::new)
                                    .collect(Collectors.toList());

                    JwtAuthentication principal = new JwtAuthentication(jwt, memberName);
                    JwtAuthenticationToken authentication = new JwtAuthenticationToken(principal, null, authorities);
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        } else {
            log.debug("SecurityContextHolder not populated with security token, as it already containd: {}", SecurityContextHolder.getContext().getAuthentication());
        }

        chain.doFilter(request, response);
    }

    private String getToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(jwtProperties.getHeader());
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    private Claims getClaims(String jwt) {
        try {
            Claims claims = Jwts
                    .parserBuilder()
                    .setSigningKey(jwtProperties.getSecret())
                    .build()
                    .parseClaimsJws(jwt)
                    .getBody();

            return claims;
        } catch (SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT signing key.");
        } catch (ExpiredJwtException e) {
            log.info("JWT has been expired.");
        } catch (UnsupportedJwtException e) {
            log.info("This JWT is unsupported format.");
        } catch (IllegalArgumentException e) {
            log.info("Invalid JWT.");
        }
        return null;
    }

}
