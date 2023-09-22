package com.codesquad.secondhand.command.adapter.out.persistence;

import com.codesquad.secondhand.command.adapter.out.persistence.imports.ChatRoomCrudRepository;
import com.codesquad.secondhand.command.domain.chat.ChatRoom;
import com.codesquad.secondhand.command.port.out.ChatRoomRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ChatRoomRepositoryImpl implements ChatRoomRepository {

    private final ChatRoomCrudRepository chatRoomCrudRepository;

    @Override
    public ChatRoom save(ChatRoom chatRoom) {
        return chatRoomCrudRepository.save(chatRoom);
    }

    @Override
    public Optional<ChatRoom> findByProductIdAndSellerIdAndBuyerId(long productId, long sellerId, long buyerId) {
        return chatRoomCrudRepository.findByProductIdAndSellerIdAndBuyerId(productId, sellerId, buyerId);
    }

    @Override
    public Optional<ChatRoom> findById(long chatRoomId) {
        return chatRoomCrudRepository.findById(chatRoomId);
    }
}
