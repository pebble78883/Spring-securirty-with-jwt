package com.example.demo.common.error;

import com.example.demo.common.dto.ApiResult;
import com.example.demo.common.error.exception.ServiceRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * @Valid, @Validate 실패 시 호출
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ApiResult> handleMethodArgumentNotValidException(final MethodArgumentNotValidException e) {
        final ErrorCode errorCode = ErrorCode.INVALID_PARAMETER;
        log.warn("handleMethodArgumentNotValidException {}", errorCode.getMessage(), e);

        final ApiResult apiResult = ApiResult.fail(errorCode);
        return new ResponseEntity<>(apiResult, HttpStatus.valueOf(errorCode.getStatus()));
    }

    /**
     * @PreAuthorize 인가 실패 시 호출
     */
    @ExceptionHandler(AccessDeniedException.class)
    protected ResponseEntity<ApiResult> handleAccessDeniedException(final AccessDeniedException e) {
        final ErrorCode errorCode = ErrorCode.ACCESS_DENIED;
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final Object principal = authentication != null ? authentication.getPrincipal() : null;
        log.warn("handleAccessDeniedException: {} {}", principal, errorCode.getMessage(), e);

        final ApiResult apiResult = ApiResult.fail(errorCode);
        return new ResponseEntity<>(apiResult, HttpStatus.valueOf(errorCode.getStatus()));
    }

    /**
     * 미리 정의 된 에러 발생 시 호출
     * 정의 된 에러 목록 : com.api.dailyseminar.common.error.ErrorCode
     */
    @ExceptionHandler(ServiceRuntimeException.class)
    protected ResponseEntity<ApiResult> handleServiceRuntimeException(final ServiceRuntimeException e) {
        final ErrorCode errorCode = e.getErrorCode();
        log.warn("handleServiceRuntimeException: {}", errorCode.getMessage(), e);

        final ApiResult apiResult = ApiResult.fail(errorCode);
        return new ResponseEntity<>(apiResult, HttpStatus.valueOf(errorCode.getStatus()));
    }

    /**
     * 명시적으로 핸들링 되지 않은 에러 발생 시 호출
     */
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ApiResult> handleException(Exception e) {
        log.error("Unexpected exception occurred: {}", e.getMessage(), e);

        final ApiResult apiResult = ApiResult.fail(ErrorCode.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(apiResult, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
