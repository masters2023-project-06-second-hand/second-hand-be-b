package com.codesquad.secondhand.application.port.out;

import com.codesquad.secondhand.domain.chat.ChatRoom;
import java.util.Optional;

public interface ChatRoomRepository {

    ChatRoom save(ChatRoom chatRoom);

    Optional<ChatRoom> findByProductIdAndSellerIdAndBuyerId(long productId, long sellerId, long buyerId);
}
