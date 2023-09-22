package com.codesquad.secondhand.command.adapter.in.web.chat.response;

import lombok.Getter;

@Getter
public class ChatRoomMessage {

    private String message;
    private Long senderId;

    public ChatRoomMessage(String message, Long senderId) {
        this.message = message;
        this.senderId = senderId;
    }
}
