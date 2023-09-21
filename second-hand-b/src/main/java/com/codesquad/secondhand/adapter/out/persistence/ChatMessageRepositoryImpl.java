package com.codesquad.secondhand.adapter.out.persistence;

import com.codesquad.secondhand.adapter.out.persistence.imports.ChatMessageCrudRepository;
import com.codesquad.secondhand.application.port.out.ChatMessageRepository;
import com.codesquad.secondhand.domain.chat.ChatMessage;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ChatMessageRepositoryImpl implements ChatMessageRepository {

    private final ChatMessageCrudRepository chatMessageCrudRepository;

    @Override
    public List<ChatMessage> findAllByChatRoomId(long chatRoomId) {
        return chatMessageCrudRepository.findAllByChatRoomIdOrderByCreatedAtAsc(chatRoomId);
    }

    @Override
    public void readChatMessagesByChatRoomIdAndNotSenderId(long chatRoomId, long memberId) {
        chatMessageCrudRepository.readChatMessagesByChatRoomIdAndNotSenderId(chatRoomId, memberId);
    }
}
