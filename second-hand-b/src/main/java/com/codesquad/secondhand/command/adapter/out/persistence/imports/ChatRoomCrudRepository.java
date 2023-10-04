package com.codesquad.secondhand.command.adapter.out.persistence.imports;

import com.codesquad.secondhand.command.domain.chat.ChatRoom;
import com.codesquad.secondhand.command.domain.chat.dto.ChatRoomDto;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ChatRoomCrudRepository extends JpaRepository<ChatRoom, Long> {

    Optional<ChatRoom> findByProductIdAndSellerIdAndBuyerId(long productId, long sellerId, long buyerId);

    @Query(value = "select room.id as id, p.id as productId, p.thumbnail_url as productThumbnailUrl, "
            + "            m.nickname as opponentName, m.profile_image as opponentThumbnailUrl, "
            + "            last_message.message as message, last_message.created_at as sendAt, "
            + "            count(message_cnt.id) as unreadMessageCount "
            + "from chat_room room "
            + "join product p on room.product_id = p.id "
            + "join member m on room.buyer_id = m.id "
            + "left join chat_message message_cnt on room.id = message_cnt.chat_room_id "
            + "                                     and message_cnt.sender_id = room.buyer_id "
            + "                                     and message_cnt.read_or_not = false "
            + "left join (select chat_room_id, message, created_at, "
            + "                  row_number() over (partition by chat_room_id order by created_at desc) rn "
            + "            from chat_message) last_message "
            + "          on room.id = last_message.chat_room_id and last_message.rn = 1 "
            + "where room.seller_id = :memberId "
            + "group by room.id, last_message.message, last_message.created_at "
            + "union all "
            + "select room.id as id, p.id as productId, p.thumbnail_url as productThumbnailUrl, "
            + "            m.nickname as opponentName, m.profile_image as opponentThumbnailUrl, "
            + "            last_message.message as message, last_message.created_at as sendAt, "
            + "            count(message_cnt.id) as unreadMessageCount "
            + "from chat_room room "
            + "join product p on room.product_id = p.id "
            + "join member m on room.seller_id = m.id "
            + "left join chat_message message_cnt on room.id = message_cnt.chat_room_id "
            + "                                     and message_cnt.sender_id = room.seller_id "
            + "                                     and message_cnt.read_or_not = false "
            + "left join (select chat_room_id, message, created_at, "
            + "                  row_number() over (partition by chat_room_id order by created_at desc) rn "
            + "            from chat_message) last_message "
            + "          on room.id = last_message.chat_room_id and last_message.rn = 1 "
            + "where room.buyer_id = :memberId "
            + "group by room.id, last_message.message, last_message.created_at "
            + "order by id",
            nativeQuery = true
    )
    List<ChatRoomDto> findAllByMemberId(long memberId);
}
