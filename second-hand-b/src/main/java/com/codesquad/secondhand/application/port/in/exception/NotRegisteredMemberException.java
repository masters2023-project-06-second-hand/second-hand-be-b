package com.codesquad.secondhand.application.port.in.exception;

import java.util.Map;

public class NotRegisteredMemberException extends BusinessException {

    private static final String STATUS = "401";
    private static final String ERROR = "사용자는 등록된 멤버가 아닙니다. 가입을 먼저 진행해주세요.";
    private static final String TOKEN_KEY = "token";

    public NotRegisteredMemberException(String token) {
        super(new ErrorResponse(STATUS, new ErrorBody(ERROR, Map.of(TOKEN_KEY, token))));
    }
}
