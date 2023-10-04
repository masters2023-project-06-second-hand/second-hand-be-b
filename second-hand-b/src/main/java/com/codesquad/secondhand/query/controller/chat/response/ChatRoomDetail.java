package com.codesquad.secondhand.query.controller.chat.response;

import com.codesquad.secondhand.command.adapter.in.web.chat.response.ChatRoomMessage;
import com.codesquad.secondhand.command.adapter.in.web.chat.response.ChatRoomProduct;
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
