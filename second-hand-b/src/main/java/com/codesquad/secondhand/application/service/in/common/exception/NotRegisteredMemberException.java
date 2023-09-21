package com.codesquad.secondhand.application.service.in.common.exception;

public class NotRegisteredMemberException extends BusinessException {

    public NotRegisteredMemberException() {
        super(ErrorCode.NOT_REGISTERED_MEMBER);
    }
}
