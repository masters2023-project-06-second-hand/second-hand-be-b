package com.codesquad.secondhand.application.port.in.exception;

import java.util.Map;

public class NotRegisteredMemberException extends BusinessException {

    private static final String STATUS = "401";
    private static final String ERROR = "사용자는 등록된 멤버가 아닙니다. 가입을 먼저 진행해주세요.";
    private final String token;

    public NotRegisteredMemberException(String token) {
        super(new ErrorResponse(STATUS, new Errors(Map.of("error", ERROR))));
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
