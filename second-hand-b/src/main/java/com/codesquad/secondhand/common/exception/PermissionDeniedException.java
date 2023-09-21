package com.codesquad.secondhand.common.exception;

public class PermissionDeniedException extends BusinessException {

    public PermissionDeniedException() {
        super(ErrorCode.PERMISSION_DENIED);
    }
}
