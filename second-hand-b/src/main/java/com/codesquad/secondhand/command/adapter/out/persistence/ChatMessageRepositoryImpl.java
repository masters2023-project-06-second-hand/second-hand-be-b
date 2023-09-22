package com.codesquad.secondhand.command.adapter.out.persistence;

import com.codesquad.secondhand.command.adapter.out.persistence.imports.ChatMessageCrudRepository;
import com.codesquad.secondhand.command.domain.chat.ChatMessage;
import com.codesquad.secondhand.command.port.out.ChatMessageRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ChatMessageRepositoryImpl implements ChatMessageRepository {

    private final ChatMessageCrudRepository chatMessageCrudRepository;

    @Override
    public ChatMessage save(ChatMessage chatMessage) {
        return chatMessageCrudRepository.save(chatMessage);
    }

    @Override
    public List<ChatMessage> findAllByChatRoomId(long chatRoomId) {
        return chatMessageCrudRepository.findAllByChatRoomIdOrderByCreatedAtAsc(chatRoomId);
    }

    @Override
    public void markMessagesAsReadByChatRoomIdAndNotSenderId(long chatRoomId, long memberId) {
        chatMessageCrudRepository.markMessagesAsReadByChatRoomIdAndNotSenderId(chatRoomId, memberId);
    }
}
