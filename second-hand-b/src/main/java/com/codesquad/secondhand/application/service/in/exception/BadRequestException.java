package com.codesquad.secondhand.application.service.in.exception;

import java.util.Map;

public class BadRequestException extends BusinessException {

    public BadRequestException() {
        super(new ErrorResponse("400", new Errors(Map.of("error", "잘못 된 요청입니다"))));
    }
}
