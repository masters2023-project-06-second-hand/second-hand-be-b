package com.codesquad.secondhand.application.service.in.exception;

public class BadRequestException extends BusinessException {

    public BadRequestException() {
        super(ErrorCode.BAD_REQUEST);
    }
}
