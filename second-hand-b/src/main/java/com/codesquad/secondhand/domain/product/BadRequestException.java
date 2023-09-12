package com.codesquad.secondhand.domain.product;

import com.codesquad.secondhand.application.port.in.exception.BusinessException;
import com.codesquad.secondhand.application.port.in.exception.ErrorResponse;
import com.codesquad.secondhand.application.port.in.exception.Errors;
import java.util.Map;

public class BadRequestException extends BusinessException {

    public BadRequestException() {
        super(new ErrorResponse("400", new Errors(Map.of("error", "잘못 된 요청입니다"))));
    }
}
