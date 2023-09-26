package com.codesquad.secondhand.common.messaging.handler;

import com.codesquad.secondhand.command.domain.units.JwtTokenProvider;
import com.codesquad.secondhand.command.port.in.ChatMessageCommandUseCase;
import com.codesquad.secondhand.command.port.in.ChatRoomCommandUseCase;
import com.codesquad.secondhand.command.service.in.MemberCommandService;
import com.codesquad.secondhand.common.exception.PermissionDeniedException;
import java.util.Date;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class StompHandler implements ChannelInterceptor {

    private static final String AUTHORIZATION = "Authorization";
    private static final int DESTINATION_PREFIX_LENGTH = 10;

    private final ChatRoomCommandUseCase chatRoomCommandUseCase;
    private final ChatMessageCommandUseCase chatMessageCommandUseCase;
    private final MemberCommandService memberService;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        handleCommand(Objects.requireNonNull(accessor.getCommand()), accessor);
        return message;
    }

    private void handleCommand(StompCommand stompCommand, StompHeaderAccessor accessor) {
        if (StompCommand.CONNECT.equals(stompCommand)) {
            connectToChatRoom(accessor);
        }
    }

    private void connectToChatRoom(StompHeaderAccessor accessor) {
        long chatRoomId = getChatRoomId(accessor);
        long memberId = validateAccessToken(accessor);
        chatRoomCommandUseCase.addChatRoomMember(chatRoomId, memberId);
        chatMessageCommandUseCase.markMessagesAsRead(chatRoomId, memberId);
    }

    private Long getChatRoomId(StompHeaderAccessor accessor) {
        String destination = Objects.requireNonNull(accessor.getDestination());
        return Long.valueOf(destination.substring(DESTINATION_PREFIX_LENGTH));
    }

    private Long validateAccessToken(StompHeaderAccessor accessor) {
        String accessToken = getAccessToken(accessor);
        Date now = new Date();
        if (JwtTokenProvider.isValidAccessToken(accessToken, now)) {
            return Long.valueOf(JwtTokenProvider.getIdFormAccessToken(accessToken));
        }
        throw new PermissionDeniedException();
    }

    private String getAccessToken(StompHeaderAccessor accessor) {
        return accessor.getFirstNativeHeader(AUTHORIZATION);
    }
}
