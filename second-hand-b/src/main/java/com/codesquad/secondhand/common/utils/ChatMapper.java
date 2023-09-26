package com.codesquad.secondhand.common.utils;

import com.codesquad.secondhand.command.adapter.in.web.chat.response.ChatRoomMessage;
import com.codesquad.secondhand.command.adapter.in.web.chat.response.ChatRoomProduct;
import com.codesquad.secondhand.command.domain.chat.ChatMessage;
import com.codesquad.secondhand.command.domain.chat.ChatRoom;
import com.codesquad.secondhand.command.domain.product.Product;
import com.codesquad.secondhand.query.controller.chat.response.ChatRoomDetail;
import java.util.List;
import java.util.stream.Collectors;

public class ChatMapper {

    private ChatMapper() {
        throw new UnsupportedOperationException();
    }

    public static ChatRoomDetail toChatRoomDetail(ChatRoom chatRoom, List<ChatMessage> chatMessages, long memberId) {
        Product product = chatRoom.getProduct();
        ChatRoomProduct chatRoomProduct = new ChatRoomProduct(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getThumbnailUrl());
        String opponentName = chatRoom.getOpponentName(memberId);

        List<ChatRoomMessage> chatRoomMessages = chatMessages.stream()
                .map(chatMessage -> new ChatRoomMessage(chatMessage.getMessage(), chatMessage.getSenderId()))
                .collect(Collectors.toList());

        return new ChatRoomDetail(chatRoomProduct, opponentName, chatRoomMessages);
    }
}
