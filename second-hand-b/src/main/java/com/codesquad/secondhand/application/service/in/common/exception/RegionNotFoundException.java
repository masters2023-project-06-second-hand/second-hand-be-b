package com.codesquad.secondhand.application.service.in.common.exception;

public class RegionNotFoundException extends BusinessException {

    public RegionNotFoundException() {
        super(ErrorCode.REGION_NOT_FOUND);
    }
}
