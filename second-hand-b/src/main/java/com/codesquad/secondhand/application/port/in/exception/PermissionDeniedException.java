package com.codesquad.secondhand.application.port.in.exception;

import java.util.List;

public class PermissionDeniedException extends BusinessException {

    private static final String STATUS = "401";
    private static final String ERROR = "이 작업을 수행하기 위한 필요한 권한이 없습니다";

    public PermissionDeniedException() {
        super(new ErrorResponse(STATUS, new Errors(List.of(ERROR))));
    }
}
