package com.codesquad.secondhand.application.port.in.exception;

public class CategoryNotFoundException extends BusinessException {

    public CategoryNotFoundException() {
        super(ErrorCode.CATEGORY_NOT_FOUND);
    }
}
