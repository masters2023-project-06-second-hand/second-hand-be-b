package com.codesquad.secondhand.application.port.in.exception;

public class NotExistsMemberRegionException extends BusinessException {

    public NotExistsMemberRegionException() {
        super(ErrorCode.NOT_EXISTS_MEMBER_REGION);
    }
}
