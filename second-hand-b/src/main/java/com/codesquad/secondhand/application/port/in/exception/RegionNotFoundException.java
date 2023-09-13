package com.codesquad.secondhand.application.port.in.exception;

public class RegionNotFoundException extends BusinessException {

    public RegionNotFoundException() {
        super(ErrorCode.REGION_NOT_FOUND);
    }
}
