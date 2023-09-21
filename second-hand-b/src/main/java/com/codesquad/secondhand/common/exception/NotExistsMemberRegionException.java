package com.codesquad.secondhand.common.exception;

public class NotExistsMemberRegionException extends BusinessException {

    public NotExistsMemberRegionException() {
        super(ErrorCode.NOT_EXISTS_MEMBER_REGION);
    }
}
