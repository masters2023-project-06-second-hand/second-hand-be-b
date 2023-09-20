package com.codesquad.secondhand.adapter.in.web.response.product;

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
