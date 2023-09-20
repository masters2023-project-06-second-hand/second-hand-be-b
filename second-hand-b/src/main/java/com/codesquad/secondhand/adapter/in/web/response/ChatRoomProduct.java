package com.codesquad.secondhand.adapter.in.web.response;

import lombok.Getter;

@Getter
public class ChatRoomProduct {

    private Long id;
    private String name;
    private int price;
    private String thumbnailUrl;

    public ChatRoomProduct(Long id, String name, int price, String thumbnailUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.thumbnailUrl = thumbnailUrl;
    }
}
