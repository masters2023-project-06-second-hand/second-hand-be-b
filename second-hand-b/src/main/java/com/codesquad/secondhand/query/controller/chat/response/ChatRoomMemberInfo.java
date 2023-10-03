package com.codesquad.secondhand.query.controller.chat.response;

import lombok.Getter;

@Getter
public class ChatRoomMemberInfo {

    private String name;
    private String thumbnailUrl;

    public ChatRoomMemberInfo(String name, String thumbnailUrl) {
        this.name = name;
        this.thumbnailUrl = thumbnailUrl;
    }
}
