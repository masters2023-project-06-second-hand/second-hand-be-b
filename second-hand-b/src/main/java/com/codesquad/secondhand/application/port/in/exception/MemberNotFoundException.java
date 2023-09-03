package com.codesquad.secondhand.application.port.in.exception;

import java.util.List;

public class MemberNotFoundException extends BusinessException {

    private static final String STATUS = "404";
    private static final String ERROR = "사용자를 찾을 수 없습니다.";

    public MemberNotFoundException() {
        super(new ErrorResponse(STATUS, new Errors(List.of(ERROR))));
    }
}
