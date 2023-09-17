package com.codesquad.secondhand.application.service.in.exception;

public class ImageNotFoundException extends BusinessException {

    public ImageNotFoundException() {
        super(ErrorCode.IMAGE_NOT_FOUND);
    }
}
