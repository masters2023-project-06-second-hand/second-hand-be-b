package com.codesquad.secondhand.application.port.in.exception;

import java.io.Serializable;

public class ErrorBody implements Serializable {

    private final String error;
    private final Object data;

    public ErrorBody(String error, Object data) {
        this.error = error;
        this.data = data;
    }

    public String getError() {
        return error;
    }

    public Object getData() {
        return data;
    }
}
