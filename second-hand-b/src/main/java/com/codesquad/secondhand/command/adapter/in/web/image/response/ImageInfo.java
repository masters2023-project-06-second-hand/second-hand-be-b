package com.codesquad.secondhand.command.adapter.in.web.image.response;

import lombok.Getter;

@Getter
public class ImageInfo {

    private final Long id;
    private final String imgUrl;

    public ImageInfo(Long id, String imgUrl) {
        this.id = id;
        this.imgUrl = imgUrl;
    }
}
