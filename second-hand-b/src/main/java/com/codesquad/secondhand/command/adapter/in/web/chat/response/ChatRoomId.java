package com.codesquad.secondhand.command.adapter.in.web.chat.response;

import lombok.Getter;

@Getter
public class ChatRoomId {

    private Long chatRoomId;

    public ChatRoomId(Long chatRoomId) {
        this.chatRoomId = chatRoomId;
    }
}
