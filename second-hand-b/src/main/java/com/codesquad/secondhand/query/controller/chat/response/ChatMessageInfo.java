package com.codesquad.secondhand.query.controller.chat.response;

import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class ChatMessageInfo {

    private String message;
    private Integer unreadMessageCount;
    private LocalDateTime sendAt;

    public ChatMessageInfo(String message, Integer unreadMessageCount, LocalDateTime sendAt) {
        this.message = message;
        this.unreadMessageCount = unreadMessageCount;
        this.sendAt = sendAt;
    }
}
