package com.codesquad.secondhand.application.service.in.exception;

import java.util.Map;

public class InvalidEntityStateException extends BusinessException {

    public InvalidEntityStateException() {
        super(new ErrorResponse("E001", new Errors(Map.of("error", "엔터티는 저장 후 유효한 ID를 가져야 하는데 null을 발견했습니다"))));
    }
}
