package com.codesquad.secondhand.application.port.in.exception;

import java.util.Map;

public class ExistsMemberRegionException extends BusinessException {

    private static final String STATUS = "400";
    private static final String ERROR = "이미 존재하는 멤버의 동네입니다.";

    public ExistsMemberRegionException() {
        super(new ErrorResponse(STATUS, new Errors(Map.of("error", ERROR))));
    }
}
