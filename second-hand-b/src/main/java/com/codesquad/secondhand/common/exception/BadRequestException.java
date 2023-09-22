package com.codesquad.secondhand.common.exception;

public class BadRequestException extends BusinessException {

    public BadRequestException() {
        super(ErrorCode.BAD_REQUEST);
    }
}
