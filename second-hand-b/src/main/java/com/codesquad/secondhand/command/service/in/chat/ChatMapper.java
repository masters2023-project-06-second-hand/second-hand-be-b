package com.codesquad.secondhand.command.service.in.chat;

import com.codesquad.secondhand.command.adapter.in.web.chat.response.ChatRoomDetail;
import com.codesquad.secondhand.command.adapter.in.web.chat.response.ChatRoomMessage;
import com.codesquad.secondhand.command.adapter.in.web.chat.response.ChatRoomProduct;
import com.codesquad.secondhand.command.domain.chat.ChatMessage;
import com.codesquad.secondhand.command.domain.chat.ChatRoom;
import com.codesquad.secondhand.command.domain.product.Product;
import java.util.List;
import java.util.stream.Collectors;

class ChatMapper {

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
