package com.codesquad.secondhand.common.exception;

public class S3UploadFailedException extends BusinessException {

    public S3UploadFailedException() {
        super(ErrorCode.REGION_NOT_FOUND);
    }
}
