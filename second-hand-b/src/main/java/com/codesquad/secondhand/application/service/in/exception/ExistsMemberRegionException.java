package com.codesquad.secondhand.application.service.in.exception;

public class ExistsMemberRegionException extends BusinessException {

    public ExistsMemberRegionException() {
        super(ErrorCode.EXISTS_MEMBER_REGION);
    }
}
