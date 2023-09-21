package com.codesquad.secondhand.application.service.in.common.exception;

public class S3UploadFailedException extends BusinessException {

    public S3UploadFailedException() {
        super(ErrorCode.REGION_NOT_FOUND);
    }
}
