package com.codesquad.secondhand.adapter.in.web.request.product;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ToggleProductLikeStatusRequest {

    @JsonProperty(value = "isLiked")
    private boolean isLiked;

    public boolean isLiked() {
        return isLiked;
    }
}
