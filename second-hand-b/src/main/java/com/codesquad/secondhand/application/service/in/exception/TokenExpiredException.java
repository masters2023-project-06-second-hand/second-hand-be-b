package com.codesquad.secondhand.application.service.in.exception;

import java.util.Map;

public class TokenExpiredException extends BusinessException {

    private static final String STATUS = "401";
    private static final String ERROR = "토큰이 유효하지 않습니다. 다시 로그인해주세요.";

    public TokenExpiredException() {
        super(new ErrorResponse(STATUS, new Errors(Map.of("error", ERROR))));
    }
}
