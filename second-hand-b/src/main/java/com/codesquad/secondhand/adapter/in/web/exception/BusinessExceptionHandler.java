package com.codesquad.secondhand.adapter.in.web.exception;

import com.codesquad.secondhand.application.port.in.exception.ErrorResponse;
import com.codesquad.secondhand.application.port.in.exception.NotRegisteredMemberException;
import com.codesquad.secondhand.application.port.in.exception.TokenExpiredException;
import java.net.URI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class BusinessExceptionHandler {

    private static final String BEARER_TYPE = "Bearer ";
    @Value(value = "${front.server.address}")
    private String frontAddress;

    @ExceptionHandler(value = NotRegisteredMemberException.class)
    public ResponseEntity<Void> notRegisteredMemberExceptionHandler(
            NotRegisteredMemberException notRegisteredMemberException) {
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(frontAddress));
        headers.set(HttpHeaders.AUTHORIZATION, BEARER_TYPE + notRegisteredMemberException.getToken());
        return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
    }

    @ExceptionHandler(value = TokenExpiredException.class)
    public ResponseEntity<ErrorResponse> tokenExpiredExceptionHandler(TokenExpiredException tokenExpiredException) {
        ErrorResponse errorResponse = tokenExpiredException.getErrorResponse();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }
}
