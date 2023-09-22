package com.codesquad.secondhand.command.adapter.out.persistence.imports;

import com.codesquad.secondhand.command.domain.chat.ChatRoom;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface ChatRoomCrudRepository extends CrudRepository<ChatRoom, Long> {

    Optional<ChatRoom> findByProductIdAndSellerIdAndBuyerId(long productId, long sellerId, long buyerId);
}
