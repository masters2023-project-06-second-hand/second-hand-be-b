package com.codesquad.secondhand.application.port.in.response;

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
