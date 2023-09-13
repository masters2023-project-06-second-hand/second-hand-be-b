package com.codesquad.secondhand.application.service.in.exception;

import java.util.Map;

public class MinimumImageRequirementException extends BusinessException {

    public MinimumImageRequirementException() {
        super(new ErrorResponse("415", new Errors(Map.of("Error", "이미지는 최소 하나는 있어야 한다"))));
    }
}
