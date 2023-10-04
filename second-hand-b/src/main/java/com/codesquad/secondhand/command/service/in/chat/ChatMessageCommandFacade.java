package com.codesquad.secondhand.command.service.in.chat;

import com.codesquad.secondhand.command.domain.chat.ChatMessage;
import com.codesquad.secondhand.command.domain.chat.ChatRoom;
import com.codesquad.secondhand.command.domain.chat.ChatRoomMember;
import com.codesquad.secondhand.command.port.in.ChatMessageCommandUseCase;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ChatMessageCommandFacade implements ChatMessageCommandUseCase {

    public static final int CHAT_ROOM_MEMBER_MAX_SIZE = 2;

    private final ChatMessageCommandService chatMessageCommandService;
    private final ChatRoomCommandService chatRoomCommandService;
    private final ChatRoomMemberCommandService chatRoomMemberCommandService;

    @Transactional
    @Override
    public void saveChatMessage(long chatRoomId, String message, long senderId) {
        ChatMessage chatMessage = toChatMessage(chatRoomId, message, senderId);
        chatMessageCommandService.save(chatMessage);
    }

    @Override
    public void markMessagesAsRead(long chatRoomId, long memberId) {
        chatMessageCommandService.markMessagesAsRead(chatRoomId, memberId);
    }

    private ChatMessage toChatMessage(long chatRoomId, String message, long senderId) {
        ChatRoom chatRoom = chatRoomCommandService.getById(chatRoomId);
        List<ChatRoomMember> chatRoomMembers = chatRoomMemberCommandService.findAllByChatRoomId(chatRoomId);
        boolean readOrNot = isChatRoomFull(chatRoomMembers);
        return new ChatMessage(
                chatRoom,
                senderId,
                message,
                readOrNot,
                LocalDateTime.now());
    }

    private boolean isChatRoomFull(List<ChatRoomMember> chatRoomMembers) {
        return chatRoomMembers.size() == CHAT_ROOM_MEMBER_MAX_SIZE;
    }
}
