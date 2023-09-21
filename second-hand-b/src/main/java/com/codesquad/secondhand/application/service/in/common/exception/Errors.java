package com.codesquad.secondhand.application.service.in.common.exception;

import com.fasterxml.jackson.annotation.JsonValue;
import java.io.Serializable;
import java.util.Map;

public class Errors implements Serializable {

    private final Map<String, String> messages;

    public Errors(Map<String, String> messages) {
        this.messages = messages;
    }

    @JsonValue
    public Map<String, String> getMessages() {
        return messages;
    }
}
