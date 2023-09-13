package com.codesquad.secondhand.application.service.in.exception;

import java.util.Map;

public class ProductNotFoundException extends BusinessException {

    private static final String STATUS = "404";
    private static final String ERROR = "상품을 찾을 수 없습니다.";

    public ProductNotFoundException() {
        super(new ErrorResponse(STATUS, new Errors(Map.of("error", ERROR))));
    }
}
