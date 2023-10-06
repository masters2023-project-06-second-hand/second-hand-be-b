package com.codesquad.secondhand.command.adapter.in.web.chat.response;

import lombok.Getter;

@Getter
public class ChatMessageResponse {

    private Long chatRoomId;
    private Message messages;

    public ChatMessageResponse(Long chatRoomId, Message messages) {
        this.chatRoomId = chatRoomId;
        this.messages = messages;
    }
}
