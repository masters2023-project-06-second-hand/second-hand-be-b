package com.codesquad.secondhand.adapter.in.web.exception;

import com.codesquad.secondhand.application.port.in.exception.BusinessException;
import com.codesquad.secondhand.application.port.in.exception.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class BusinessExceptionHandler {

    @ExceptionHandler(value = BusinessException.class)
    public ResponseEntity<ErrorResponse> businessExceptionHandler(BusinessException businessException) {
        return ResponseEntity.badRequest()
                .body(businessException.getErrorResponse());
    }
}
