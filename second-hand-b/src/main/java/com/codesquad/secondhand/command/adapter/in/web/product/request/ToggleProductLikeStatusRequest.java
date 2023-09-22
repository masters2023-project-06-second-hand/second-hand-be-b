package com.codesquad.secondhand.command.adapter.in.web.product.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ToggleProductLikeStatusRequest {

    @JsonProperty(value = "isLiked")
    private boolean isLiked;

    public boolean isLiked() {
        return isLiked;
    }
}
