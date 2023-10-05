package com.codesquad.secondhand.command.service.in.chat;

import com.codesquad.secondhand.command.adapter.in.web.chat.response.ChatNotification;
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
    private final NotificationService notificationService;

    @Transactional
    @Override
    public void saveChatMessage(long chatRoomId, String message, long senderId) {
        ChatRoom chatRoom = chatRoomCommandService.getById(chatRoomId);
        ChatMessage chatMessage = toChatMessage(chatRoom, message, senderId);
        chatMessageCommandService.save(chatMessage);

        long opponentId = chatRoom.getOpponentId(senderId);
        notifyChatMessage(chatMessage, chatRoomId, opponentId);
    }

    @Override
    public void markMessagesAsRead(long chatRoomId, long memberId) {
        chatMessageCommandService.markMessagesAsRead(chatRoomId, memberId);
    }

    private ChatMessage toChatMessage(ChatRoom chatRoom, String message, long senderId) {
        Long chatRoomId = chatRoom.getId();
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

    private void notifyChatMessage(ChatMessage chatMessage, long chatRoomId, long opponentId) {
        if (!chatMessage.isReadOrNot()) {
            String message = chatMessage.getMessage();
            ChatNotification chatNotification = new ChatNotification(chatRoomId, message);
            notificationService.notify(opponentId, chatNotification);
        }
    }
}
