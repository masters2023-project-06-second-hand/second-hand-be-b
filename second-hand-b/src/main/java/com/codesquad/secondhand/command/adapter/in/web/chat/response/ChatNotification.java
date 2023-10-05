package com.codesquad.secondhand.command.adapter.in.web.chat.response;

import lombok.Getter;

@Getter
public class ChatNotification {

    private Long chatRoomId;
    private String message;

    public ChatNotification(Long chatRoomId, String message) {
        this.chatRoomId = chatRoomId;
        this.message = message;
    }
}
