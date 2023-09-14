package com.codesquad.secondhand.adapter.in.web.exception;

import com.codesquad.secondhand.application.service.in.exception.BusinessException;
import com.codesquad.secondhand.application.service.in.exception.ErrorCode;
import com.codesquad.secondhand.application.service.in.exception.ErrorResponse;
import com.codesquad.secondhand.application.service.in.exception.Errors;
import com.codesquad.secondhand.application.service.in.exception.NotRegisteredMemberException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();

        exception.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );
        ErrorResponse errorResponse = new ErrorResponse(ErrorCode.BAD_REQUEST.getStatus(), new Errors(errors));
        return ResponseEntity.badRequest().body(errorResponse);
    }

    private ResponseEntity<ErrorResponse> handleBusinessException(BusinessException exception) {
        ErrorCode errorCode = exception.getErrorCode();
        ErrorResponse errorResponse = new ErrorResponse(errorCode.getStatus(),
                new Errors(Map.of(ERROR_KEY, errorCode.getMessage())));
        return ResponseEntity.status(errorCode.getHttpStatus()).body(errorResponse);
    }
}
