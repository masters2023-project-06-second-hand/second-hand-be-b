package com.codesquad.secondhand.application.port.in.exception;

public class TokenExpiredException extends BusinessException {

    public TokenExpiredException() {
        super(ErrorCode.TOKEN_EXPIRED);
    }
}
