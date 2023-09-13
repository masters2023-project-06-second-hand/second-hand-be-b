package com.codesquad.secondhand.adapter.in.web.exception;

import com.codesquad.secondhand.application.service.in.exception.ErrorResponse;
import com.codesquad.secondhand.application.service.in.exception.Errors;
import com.codesquad.secondhand.application.service.in.exception.NotRegisteredMemberException;
import com.codesquad.secondhand.application.service.in.exception.TokenExpiredException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();

        exception.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        ErrorResponse errorResponse = new ErrorResponse("E002", new Errors(errors));

        return ResponseEntity.badRequest().body(errorResponse);
    }
}
