package com.codesquad.secondhand.adapter.in.web.response.product;

import lombok.Getter;

@Getter
public class ProductWriter {

    private Long id;
    private String nickname;

    public ProductWriter(Long id, String nickname) {
        this.id = id;
        this.nickname = nickname;
    }
}
