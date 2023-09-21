package com.codesquad.secondhand.application.service.in.common.exception;

public class BadRequestException extends BusinessException {

    public BadRequestException() {
        super(ErrorCode.BAD_REQUEST);
    }
}
