package com.codesquad.secondhand.adapter.in.web.exception;

import com.codesquad.secondhand.application.port.in.exception.ErrorResponse;
import com.codesquad.secondhand.application.port.in.exception.NotRegisteredMemberException;
import com.codesquad.secondhand.application.port.in.exception.TokenExpiredException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.view.RedirectView;

@ControllerAdvice
public class BusinessExceptionHandler {

    private static final String BEARER_TYPE = "Bearer ";
    @Value(value = "경${front.server.address}")
    private String frontAddress;

    @ExceptionHandler(value = NotRegisteredMemberException.class)
    public RedirectView notRegisteredMemberExceptionHandler(
            NotRegisteredMemberException notRegisteredMemberException) {
        return new RedirectView(frontAddress + "/join?signupToken=" + notRegisteredMemberException.getToken());
    }

    @ExceptionHandler(value = TokenExpiredException.class)
    public ResponseEntity<ErrorResponse> tokenExpiredExceptionHandler(TokenExpiredException tokenExpiredException) {
        ErrorResponse errorResponse = tokenExpiredException.getErrorResponse();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }
}
