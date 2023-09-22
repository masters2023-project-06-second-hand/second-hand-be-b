package com.codesquad.secondhand.command.adapter.in.web.chat.request;

public class ChatMessageRequest {

    private Long chatRoomId;
    private String message;
    private Long senderId;

    public Long getChatRoomId() {
        return chatRoomId;
    }

    public String getMessage() {
        return message;
    }

    public Long getSenderId() {
        return senderId;
    }
}
