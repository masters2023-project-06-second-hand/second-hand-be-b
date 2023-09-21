package com.codesquad.secondhand.application.port.out;

import com.codesquad.secondhand.domain.chat.ChatMessage;
import java.util.List;

public interface ChatMessageRepository {

    List<ChatMessage> findAllByChatRoomId(long chatRoomId);

    void readChatMessagesByChatRoomIdAndNotSenderId(long chatRoomId, long memberId);
}
