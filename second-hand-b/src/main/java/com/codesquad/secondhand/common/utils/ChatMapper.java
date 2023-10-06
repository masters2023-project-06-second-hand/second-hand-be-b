package com.codesquad.secondhand.common.utils;

import com.codesquad.secondhand.command.adapter.in.web.chat.request.ChatMessageRequest;
import com.codesquad.secondhand.command.adapter.in.web.chat.response.ChatMessageResponse;
import com.codesquad.secondhand.command.adapter.in.web.chat.response.ChatRoomMessage;
import com.codesquad.secondhand.command.adapter.in.web.chat.response.ChatRoomProduct;
import com.codesquad.secondhand.command.adapter.in.web.chat.response.Message;
import com.codesquad.secondhand.command.domain.chat.ChatMessage;
import com.codesquad.secondhand.command.domain.chat.ChatRoom;
import com.codesquad.secondhand.command.domain.product.Product;
import com.codesquad.secondhand.query.controller.chat.response.ChatMessageInfo;
import com.codesquad.secondhand.query.controller.chat.response.ChatRoomDetail;
import com.codesquad.secondhand.query.controller.chat.response.ChatRoomInfo;
import com.codesquad.secondhand.query.controller.chat.response.ChatRoomMemberInfo;
import com.codesquad.secondhand.query.controller.chat.response.ChatRoomProductInfo;
import com.codesquad.secondhand.query.service.dto.ChatRoomDto;
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

    public static ChatRoomInfo toChatRoomInfo(ChatRoomDto chatRoomDto) {
        ChatRoomProductInfo chatRoomProductInfo = new ChatRoomProductInfo(chatRoomDto.getProductId(),
                chatRoomDto.getProductThumbnailUrl());
        ChatRoomMemberInfo chatRoomMemberInfo = new ChatRoomMemberInfo(chatRoomDto.getOpponentName(),
                chatRoomDto.getOpponentThumbnailUrl());
        ChatMessageInfo chatMessageInfo = new ChatMessageInfo(chatRoomDto.getMessage(),
                chatRoomDto.getUnreadMessageCount(),
                chatRoomDto.getSendAt());

        return new ChatRoomInfo(
                chatRoomDto.getId(),
                chatRoomProductInfo,
                chatRoomMemberInfo,
                chatMessageInfo
        );
    }

    public static ChatMessageResponse toChatMessageResponse(ChatMessageRequest chatMessageRequest) {
        Message message = new Message(chatMessageRequest.getMessage(), chatMessageRequest.getSenderId());
        return new ChatMessageResponse(chatMessageRequest.getChatRoomId(), message);
    }
}
