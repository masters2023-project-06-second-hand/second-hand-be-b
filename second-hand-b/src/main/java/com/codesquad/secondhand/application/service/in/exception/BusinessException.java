package com.codesquad.secondhand.application.service.in.exception;

public abstract class BusinessException extends RuntimeException {

    private final ErrorResponse errorResponse;

    protected BusinessException(ErrorResponse errorResponse) {
        super(errorResponse.getStatus());
        this.errorResponse = errorResponse;
    }

    public ErrorResponse getErrorResponse() {
        return errorResponse;
    }
}
