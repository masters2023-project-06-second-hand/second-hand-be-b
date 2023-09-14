package com.codesquad.secondhand.application.service.in.exception;

public class MinimumImageRequirementException extends BusinessException {

    public MinimumImageRequirementException() {
        super(ErrorCode.MINIMUM_IMAGE_REQUIRED);
    }
}
