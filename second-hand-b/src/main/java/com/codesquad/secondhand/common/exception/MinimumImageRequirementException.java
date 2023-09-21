package com.codesquad.secondhand.common.exception;

public class MinimumImageRequirementException extends BusinessException {

    public MinimumImageRequirementException() {
        super(ErrorCode.MINIMUM_IMAGE_REQUIRED);
    }
}
