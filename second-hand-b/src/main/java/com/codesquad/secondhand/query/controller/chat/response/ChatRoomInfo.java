package com.codesquad.secondhand.query.controller.chat.response;

import lombok.Getter;

@Getter
public class ChatRoomInfo {

    private Long id;
    private ChatRoomProductInfo product;
    private ChatRoomMemberInfo opponent;
    private ChatMessageInfo message;

    public ChatRoomInfo(Long id, ChatRoomProductInfo product, ChatRoomMemberInfo opponent, ChatMessageInfo message) {
        this.id = id;
        this.product = product;
        this.opponent = opponent;
        this.message = message;
    }
}
