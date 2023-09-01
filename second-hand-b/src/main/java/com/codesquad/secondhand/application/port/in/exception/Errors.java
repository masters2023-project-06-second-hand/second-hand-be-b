package com.codesquad.secondhand.application.port.in.exception;

import java.io.Serializable;
import java.util.List;

public class Errors implements Serializable {

    private final List<String> messages;

    public Errors(List<String> messages) {
        this.messages = messages;
    }

    public List<String> getMessages() {
        return messages;
    }
}
