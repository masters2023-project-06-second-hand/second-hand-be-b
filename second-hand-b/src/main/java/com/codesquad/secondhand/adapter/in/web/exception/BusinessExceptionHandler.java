package com.codesquad.secondhand.adapter.in.web.exception;

import com.codesquad.secondhand.application.port.in.exception.ErrorResponse;
import com.codesquad.secondhand.application.port.in.exception.NotRegisteredMemberException;
import com.codesquad.secondhand.application.port.in.exception.TokenExpiredException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class BusinessExceptionHandler {

    @ExceptionHandler(value = NotRegisteredMemberException.class)
    public ResponseEntity<ErrorResponse> notRegisteredMemberExceptionHandler(
            NotRegisteredMemberException notRegisteredMemberException) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(notRegisteredMemberException.getErrorResponse());
    }

    @ExceptionHandler(value = TokenExpiredException.class)
    public ResponseEntity<ErrorResponse> tokenExpiredExceptionHandler(TokenExpiredException tokenExpiredException) {
        ErrorResponse errorResponse = tokenExpiredException.getErrorResponse();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }
}
