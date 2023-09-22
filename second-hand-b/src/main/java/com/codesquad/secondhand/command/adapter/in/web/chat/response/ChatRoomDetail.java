package com.codesquad.secondhand.command.adapter.in.web.chat.response;

import java.util.List;
import lombok.Getter;

@Getter
public class ChatRoomDetail {

    private ChatRoomProduct product;
    private String opponentName;
    private List<ChatRoomMessage> messages;

    public ChatRoomDetail(ChatRoomProduct product, String opponentName, List<ChatRoomMessage> messages) {
        this.product = product;
        this.opponentName = opponentName;
        this.messages = messages;
    }
}
