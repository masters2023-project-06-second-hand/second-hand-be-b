package com.codesquad.secondhand.application.port.in.response;

import lombok.Getter;

@Getter
public class ImageInfo {

    private Long id;
    private String imgUrl;

    public ImageInfo(Long id, String imgUrl) {
        this.id = id;
        this.imgUrl = imgUrl;
    }
}
