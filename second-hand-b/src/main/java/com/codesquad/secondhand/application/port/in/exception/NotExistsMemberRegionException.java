package com.codesquad.secondhand.application.port.in.exception;

import java.util.Map;

public class NotExistsMemberRegionException extends BusinessException {

    private static final String STATUS = "400";
    private static final String ERROR = "회원의 동네와 일치하지 않습니다.";

    public NotExistsMemberRegionException() {
        super(new ErrorResponse(STATUS, new Errors(Map.of("error", ERROR))));
    }
}
