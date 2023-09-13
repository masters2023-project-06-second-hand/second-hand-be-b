package com.codesquad.secondhand.application.port.in.exception;

public class PermissionDeniedException extends BusinessException {

    public PermissionDeniedException() {
        super(ErrorCode.PERMISSION_DENIED);
    }
}
