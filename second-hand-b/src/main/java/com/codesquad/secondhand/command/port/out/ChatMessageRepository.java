package com.codesquad.secondhand.command.port.out;

import com.codesquad.secondhand.command.domain.chat.ChatMessage;
import java.util.List;

public interface ChatMessageRepository {

    ChatMessage save(ChatMessage chatMessage);

    List<ChatMessage> findAllByChatRoomId(long chatRoomId);

    void markMessagesAsReadByChatRoomIdAndNotSenderId(long chatRoomId, long memberId);
}
