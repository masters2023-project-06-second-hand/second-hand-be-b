package com.codesquad.secondhand.application.port.in.exception;

public class NotRegisteredMemberException extends BusinessException {

    public NotRegisteredMemberException() {
        super(ErrorCode.NOT_REGISTERED_MEMBER);
    }
}
