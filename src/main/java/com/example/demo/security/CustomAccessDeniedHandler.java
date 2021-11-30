package com.example.demo.security;

import com.example.demo.common.dto.ApiResult;
import com.example.demo.common.error.ErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        final ErrorCode errorCode = ErrorCode.ACCESS_DENIED;
        final ApiResult res = ApiResult.fail(errorCode);
        response.setHeader("content-type", "application/json");
        response.setStatus(errorCode.getStatus());
        String json = new ObjectMapper().writeValueAsString(res);
        response.getWriter().write(json);
        response.getWriter().flush();
        response.getWriter().close();
    }

}
