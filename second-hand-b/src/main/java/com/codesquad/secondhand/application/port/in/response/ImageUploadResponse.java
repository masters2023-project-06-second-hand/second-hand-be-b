package com.codesquad.secondhand.application.port.in.response;

import lombok.Getter;

@Getter
public class ImageUploadResponse {

    private Long id;
    private String imgUrl;

    public ImageUploadResponse(Long id, String imgUrl) {
        this.id = id;
        this.imgUrl = imgUrl;
    }
}
