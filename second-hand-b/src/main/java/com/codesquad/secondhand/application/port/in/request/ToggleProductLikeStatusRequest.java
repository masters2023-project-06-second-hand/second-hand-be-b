package com.codesquad.secondhand.application.port.in.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ToggleProductLikeStatusRequest {

    @JsonProperty(value = "isLiked")
    private boolean isLiked;

    public boolean isLiked() {
        return isLiked;
    }
}
