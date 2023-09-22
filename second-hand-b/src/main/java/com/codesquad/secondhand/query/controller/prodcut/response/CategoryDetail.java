package com.codesquad.secondhand.query.controller.prodcut.response;

import lombok.Getter;

@Getter
public class CategoryDetail {

    private final Long id;
    private final String name;
    private final String imgUrl;

    public CategoryDetail(Long id, String name, String imgUrl) {
        this.id = id;
        this.name = name;
        this.imgUrl = imgUrl;
    }
}
