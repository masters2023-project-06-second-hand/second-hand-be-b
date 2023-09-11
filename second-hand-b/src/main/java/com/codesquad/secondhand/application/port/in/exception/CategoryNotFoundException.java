package com.codesquad.secondhand.application.port.in.exception;

import java.util.Map;

public class CategoryNotFoundException extends BusinessException {

    private static final String STATUS = "404";
    private static final String ERROR = "카테고리를 찾을 수 없습니다.";

    public CategoryNotFoundException() {
        super(new ErrorResponse(STATUS, new Errors(Map.of("error", ERROR))));
    }
}
