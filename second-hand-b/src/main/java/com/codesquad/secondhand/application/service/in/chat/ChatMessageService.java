package com.codesquad.secondhand.application.service.in.chat;

import com.codesquad.secondhand.application.port.out.ChatMessageRepository;
import com.codesquad.secondhand.domain.chat.ChatMessage;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;

    public List<ChatMessage> getChatMessagesByChatRoomId(long chatRoomId) {
        return chatMessageRepository.findAllByChatRoomId(chatRoomId);
    }

    public void readChatMessages(long chatRoomId, long memberId) {
        chatMessageRepository.readChatMessagesByChatRoomIdAndNotSenderId(chatRoomId, memberId);
    }
}
