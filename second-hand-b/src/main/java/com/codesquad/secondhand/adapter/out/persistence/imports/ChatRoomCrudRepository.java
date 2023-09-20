package com.codesquad.secondhand.adapter.out.persistence.imports;

import com.codesquad.secondhand.domain.chat.ChatRoom;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface ChatRoomCrudRepository extends CrudRepository<ChatRoom, Long> {

    Optional<ChatRoom> findByProductIdAndSellerIdAndBuyerId(long productId, long sellerId, long buyerId);
}
