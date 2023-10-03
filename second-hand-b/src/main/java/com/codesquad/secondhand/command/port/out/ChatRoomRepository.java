package com.codesquad.secondhand.command.port.out;

import com.codesquad.secondhand.command.domain.chat.ChatRoom;
import com.codesquad.secondhand.command.domain.chat.dto.ChatRoomDto;
import java.util.List;
import java.util.Optional;

public interface ChatRoomRepository {

    ChatRoom save(ChatRoom chatRoom);

    Optional<ChatRoom> findByProductIdAndSellerIdAndBuyerId(long productId, long sellerId, long buyerId);

    Optional<ChatRoom> findById(long chatRoomId);

    List<ChatRoomDto> findAllByMemberId(long memberId);
}
