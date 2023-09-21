package com.codesquad.secondhand.application.service.in.exception;

public class MinimumRegionRequirementException extends BusinessException {

    public MinimumRegionRequirementException() {
        super(ErrorCode.MINIMUM_IMAGE_REQUIRED);
    }
}
