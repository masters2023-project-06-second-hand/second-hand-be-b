package com.codesquad.secondhand.application.service.in.chat;

import com.codesquad.secondhand.application.port.out.ChatRoomRepository;
import com.codesquad.secondhand.application.service.in.exception.ChatRoomNotFoundException;
import com.codesquad.secondhand.domain.chat.ChatRoom;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ChatRoomService {

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
