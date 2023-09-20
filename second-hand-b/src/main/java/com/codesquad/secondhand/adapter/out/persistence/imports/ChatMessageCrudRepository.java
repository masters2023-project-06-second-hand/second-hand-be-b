package com.codesquad.secondhand.adapter.out.persistence.imports;

import com.codesquad.secondhand.domain.chat.ChatMessage;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface ChatMessageCrudRepository extends CrudRepository<ChatMessage, Long> {

    List<ChatMessage> findAllByChatRoomIdOrderByCreatedAtAsc(long chatRoomId);
}
