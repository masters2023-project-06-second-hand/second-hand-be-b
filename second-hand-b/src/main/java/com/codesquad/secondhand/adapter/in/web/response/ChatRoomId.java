package com.codesquad.secondhand.adapter.in.web.response;

import lombok.Getter;

@Getter
public class ChatRoomId {

    private Long chatRoomId;

    public ChatRoomId(Long chatRoomId) {
        this.chatRoomId = chatRoomId;
    }
}
