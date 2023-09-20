package com.codesquad.secondhand.application.service.in.chat;

import com.codesquad.secondhand.adapter.in.web.response.ChatRoomDetail;
import com.codesquad.secondhand.adapter.in.web.response.ChatRoomMessage;
import com.codesquad.secondhand.adapter.in.web.response.ChatRoomProduct;
import com.codesquad.secondhand.domain.chat.ChatMessage;
import com.codesquad.secondhand.domain.chat.ChatRoom;
import com.codesquad.secondhand.domain.member.Member;
import com.codesquad.secondhand.domain.product.Product;
import java.util.List;
import java.util.stream.Collectors;

class ChatMapper {

    private ChatMapper() {
        throw new UnsupportedOperationException();
    }

    public static ChatRoomDetail toChatRoomDetail(ChatRoom chatRoom, List<ChatMessage> chatMessages, Member member) {
        Product product = chatRoom.getProduct();
        ChatRoomProduct chatRoomProduct = new ChatRoomProduct(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getThumbnailUrl());
        String opponentName = chatRoom.getOpponentName(member.getId());

        List<ChatRoomMessage> chatRoomMessages = chatMessages.stream()
                .map(chatMessage -> new ChatRoomMessage(chatMessage.getMessage(), chatMessage.getSenderId()))
                .collect(Collectors.toList());

        return new ChatRoomDetail(chatRoomProduct, opponentName, chatRoomMessages);
    }
}
