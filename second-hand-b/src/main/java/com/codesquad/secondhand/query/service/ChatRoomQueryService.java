package com.codesquad.secondhand.query.service;

import com.codesquad.secondhand.command.domain.chat.ChatRoom;
import com.codesquad.secondhand.command.port.out.ChatRoomRepository;
import com.codesquad.secondhand.common.exception.ChatRoomNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ChatRoomQueryService {

    private final ChatRoomRepository chatRoomRepository;

    ChatRoom getById(long chatRoomId) {
        return chatRoomRepository.findById(chatRoomId)
                .orElseThrow(ChatRoomNotFoundException::new);
    }
}
