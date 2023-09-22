package com.codesquad.secondhand.command.port.out;

import com.codesquad.secondhand.command.domain.chat.ChatRoom;
import java.util.Optional;

public interface ChatRoomRepository {

    ChatRoom save(ChatRoom chatRoom);

    Optional<ChatRoom> findByProductIdAndSellerIdAndBuyerId(long productId, long sellerId, long buyerId);

    Optional<ChatRoom> findById(long chatRoomId);
}
