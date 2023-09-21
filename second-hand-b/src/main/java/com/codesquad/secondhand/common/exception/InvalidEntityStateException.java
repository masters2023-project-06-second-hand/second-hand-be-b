package com.codesquad.secondhand.common.exception;

public class InvalidEntityStateException extends BusinessException {

    public InvalidEntityStateException() {
        super(ErrorCode.INVALID_ENTITY_STATE);
    }
}
