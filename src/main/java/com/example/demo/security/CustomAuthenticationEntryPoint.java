package com.example.demo.security;

import com.example.demo.common.dto.ApiResult;
import com.example.demo.common.error.ErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        final ErrorCode errorCode = ErrorCode.UNAUTHORIZED;
        final ApiResult res = ApiResult.fail(errorCode);
        response.setHeader("content-type", "application/json");
        response.setStatus(errorCode.getStatus());
        String json = new ObjectMapper().writeValueAsString(res);
        response.getWriter().write(json);
        response.getWriter().flush();
        response.getWriter().close();
    }

}
