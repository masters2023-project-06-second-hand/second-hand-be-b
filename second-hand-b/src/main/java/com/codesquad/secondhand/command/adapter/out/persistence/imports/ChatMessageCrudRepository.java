package com.codesquad.secondhand.command.adapter.out.persistence.imports;

import com.codesquad.secondhand.command.domain.chat.ChatMessage;
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ChatMessageCrudRepository extends CrudRepository<ChatMessage, Long> {

    List<ChatMessage> findAllByChatRoomIdOrderByCreatedAtAsc(long chatRoomId);

    @Modifying
    @Query(value = "update chat_message "
            + "set read_or_not = true "
            + "where chat_room_id = :chatRoomId "
            + "and sender_id <> :memberId "
            + "and read_or_not = false",
            nativeQuery = true)
    void markMessagesAsReadByChatRoomIdAndNotSenderId(long chatRoomId, long memberId);
}
