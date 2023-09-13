package com.codesquad.secondhand.application.service.in.exception;

public class NotExistsMemberRegionException extends BusinessException {

    public NotExistsMemberRegionException() {
        super(ErrorCode.NOT_EXISTS_MEMBER_REGION);
    }
}
