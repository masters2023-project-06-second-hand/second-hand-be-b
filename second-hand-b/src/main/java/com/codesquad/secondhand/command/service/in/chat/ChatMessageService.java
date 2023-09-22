package com.codesquad.secondhand.command.service.in.chat;

import com.codesquad.secondhand.command.domain.chat.ChatMessage;
import com.codesquad.secondhand.command.port.out.ChatMessageRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;

    public ChatMessage save(ChatMessage chatMessage) {
        return chatMessageRepository.save(chatMessage);
    }

    public List<ChatMessage> getChatMessagesByChatRoomId(long chatRoomId) {
        return chatMessageRepository.findAllByChatRoomId(chatRoomId);
    }

    public void markMessagesAsRead(long chatRoomId, long memberId) {
        chatMessageRepository.markMessagesAsReadByChatRoomIdAndNotSenderId(chatRoomId, memberId);
    }
}
