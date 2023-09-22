package com.codesquad.secondhand.common.utils;

import lombok.Getter;

@Getter
public class ProductWriter {

    private final Long id;
    private final String nickname;

    public ProductWriter(Long id, String nickname) {
        this.id = id;
        this.nickname = nickname;
    }
}
