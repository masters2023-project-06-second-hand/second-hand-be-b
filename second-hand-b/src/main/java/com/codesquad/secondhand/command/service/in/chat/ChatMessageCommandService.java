package com.codesquad.secondhand.command.service.in.chat;

import com.codesquad.secondhand.command.domain.chat.ChatMessage;
import com.codesquad.secondhand.command.port.out.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ChatMessageCommandService {

    private final ChatMessageRepository chatMessageRepository;

    public ChatMessage save(ChatMessage chatMessage) {
        return chatMessageRepository.save(chatMessage);
    }

    public void markMessagesAsRead(long chatRoomId, long memberId) {
        chatMessageRepository.markMessagesAsReadByChatRoomIdAndNotSenderId(chatRoomId, memberId);
    }
}
