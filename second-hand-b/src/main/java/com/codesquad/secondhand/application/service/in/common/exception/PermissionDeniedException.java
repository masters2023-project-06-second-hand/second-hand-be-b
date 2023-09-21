package com.codesquad.secondhand.application.service.in.common.exception;

public class PermissionDeniedException extends BusinessException {

    public PermissionDeniedException() {
        super(ErrorCode.PERMISSION_DENIED);
    }
}
