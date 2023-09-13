package com.codesquad.secondhand.adapter.in.web.exception;

import com.codesquad.secondhand.application.service.in.exception.BusinessException;
import com.codesquad.secondhand.application.service.in.exception.ErrorCode;
import com.codesquad.secondhand.application.service.in.exception.ErrorResponse;
import com.codesquad.secondhand.application.service.in.exception.Errors;
import com.codesquad.secondhand.application.service.in.exception.NotRegisteredMemberException;
import com.codesquad.secondhand.application.service.in.exception.TokenExpiredException;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    public static final String ERROR_KEY = "error";

    @ExceptionHandler(value = BusinessException.class)
    public ResponseEntity<ErrorResponse> businessExceptionHandlerHandler(BusinessException businessException) {
        return handleBusinessException(businessException);
    }

    @ExceptionHandler(value = NotRegisteredMemberException.class)
    public ResponseEntity<ErrorResponse> notRegisteredMemberExceptionHandler(
            NotRegisteredMemberException notRegisteredMemberException) {
        return handleBusinessException(notRegisteredMemberException);
    }

    @ExceptionHandler(value = TokenExpiredException.class)
    public ResponseEntity<ErrorResponse> tokenExpiredExceptionHandler(TokenExpiredException tokenExpiredException) {
        return handleBusinessException(tokenExpiredException);
    }

    private ResponseEntity<ErrorResponse> handleBusinessException(BusinessException exception) {
        ErrorCode errorCode = exception.getErrorCode();
        ErrorResponse errorResponse = new ErrorResponse(errorCode.getStatus(),
                new Errors(Map.of(ERROR_KEY, errorCode.getMessage())));
        return ResponseEntity.status(errorCode.getHttpStatus()).body(errorResponse);
    }
}
