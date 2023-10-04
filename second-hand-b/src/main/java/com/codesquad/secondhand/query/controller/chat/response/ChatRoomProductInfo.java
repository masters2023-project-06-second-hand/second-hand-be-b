package com.codesquad.secondhand.query.controller.chat.response;

import lombok.Getter;

@Getter
public class ChatRoomProductInfo {

    private Long id;
    private String thumbnailUrl;

    public ChatRoomProductInfo(Long id, String thumbnailUrl) {
        this.id = id;
        this.thumbnailUrl = thumbnailUrl;
    }
}
