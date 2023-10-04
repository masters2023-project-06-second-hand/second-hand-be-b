package com.codesquad.secondhand.command.service.in.chat;

import com.codesquad.secondhand.command.domain.chat.ChatRoom;
import com.codesquad.secondhand.command.port.out.ChatRoomRepository;
import com.codesquad.secondhand.common.exception.ChatRoomNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ChatRoomCommandService {

    private final ChatRoomRepository chatRoomRepository;

    ChatRoom save(ChatRoom chatRoom) {
        return chatRoomRepository.save(chatRoom);
    }

    ChatRoom getChatRoomId(long productId, long sellerId, long buyerId) {
        return chatRoomRepository.findByProductIdAndSellerIdAndBuyerId(productId, sellerId, buyerId)
                .orElseThrow(ChatRoomNotFoundException::new);
    }

    ChatRoom getById(long chatRoomId) {
        return chatRoomRepository.findById(chatRoomId)
                .orElseThrow(ChatRoomNotFoundException::new);
    }
}
