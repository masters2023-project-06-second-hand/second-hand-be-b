package com.codesquad.secondhand.query.service;

import com.codesquad.secondhand.command.domain.chat.ChatMessage;
import com.codesquad.secondhand.command.port.out.ChatMessageRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ChatMessageQueryService {

    private final ChatMessageRepository chatMessageRepository;

    public List<ChatMessage> getChatMessagesByChatRoomId(long chatRoomId) {
        return chatMessageRepository.findAllByChatRoomId(chatRoomId);
    }
}
