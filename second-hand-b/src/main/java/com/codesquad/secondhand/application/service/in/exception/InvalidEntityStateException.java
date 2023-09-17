package com.codesquad.secondhand.application.service.in.exception;

public class InvalidEntityStateException extends BusinessException {

    public InvalidEntityStateException() {
        super(ErrorCode.INVALID_ENTITY_STATE);
    }
}
