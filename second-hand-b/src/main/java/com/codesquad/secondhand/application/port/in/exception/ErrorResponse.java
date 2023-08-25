package com.codesquad.secondhand.application.port.in.exception;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;

public class ErrorResponse implements Serializable {

    private final String status;
    private final ErrorBody errorBody;

    public ErrorResponse(String status, ErrorBody errorBody) {
        this.status = status;
        this.errorBody = errorBody;
    }

    @JsonIgnore
    public String getMessage() {
        return errorBody.getError();
    }

    public String getStatus() {
        return status;
    }

    @JsonProperty(value = "message")
    public ErrorBody getErrorBody() {
        return errorBody;
    }
}
