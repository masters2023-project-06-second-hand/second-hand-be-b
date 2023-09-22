package com.codesquad.secondhand.common.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;

public class ErrorResponse implements Serializable {

    private final String status;
    @JsonProperty(value = "message")
    private final Errors errors;

    public ErrorResponse(String status, Errors errors) {
        this.status = status;
        this.errors = errors;
    }

    public String getStatus() {
        return status;
    }

    public Errors getErrors() {
        return errors;
    }
}
