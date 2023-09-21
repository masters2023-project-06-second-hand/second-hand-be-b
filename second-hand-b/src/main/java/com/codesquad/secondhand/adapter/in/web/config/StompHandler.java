package com.codesquad.secondhand.adapter.in.web.config;

import com.codesquad.secondhand.application.port.in.ChatUseCase;
import com.codesquad.secondhand.application.service.in.MemberService;
import com.codesquad.secondhand.application.service.in.exception.TokenExpiredException;
import com.codesquad.secondhand.domain.member.Member;
import com.codesquad.secondhand.domain.units.JwtTokenProvider;
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

    private final ChatUseCase chatUseCase;
    private final MemberService memberService;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        handleMessage(Objects.requireNonNull(accessor.getCommand()), accessor);
        return message;
    }

    public void handleMessage(StompCommand stompCommand, StompHeaderAccessor accessor) {
        if (StompCommand.CONNECT.equals(stompCommand)) {
            connectToChatRoom(accessor);
        }
    }

    private void connectToChatRoom(StompHeaderAccessor accessor) {
        Long chatRoomId = getChatRoomId(accessor);
        Long memberId = validateAccessToken(accessor);
        chatUseCase.addChatRoomMember(chatRoomId, memberId);
        chatUseCase.markMessagesAsRead(chatRoomId, memberId);
    }

    private Long getChatRoomId(StompHeaderAccessor accessor) {
        String destination = Objects.requireNonNull(accessor.getDestination());
        return Long.valueOf(destination.substring(DESTINATION_PREFIX_LENGTH));
    }

    private Long validateAccessToken(StompHeaderAccessor accessor) {
        String accessToken = getAccessToken(accessor);
        Date now = new Date();
        if (JwtTokenProvider.isValidAccessToken(accessToken, now) && JwtTokenProvider.isAccessToken(accessToken)) {
            String email = JwtTokenProvider.getEmailFromAccessToken(accessToken);
            Member member = memberService.getByEmail(email);
            return member.getId();
        } else {
            throw new TokenExpiredException();
        }
    }

    private String getAccessToken(StompHeaderAccessor accessor) {
        return accessor.getFirstNativeHeader(AUTHORIZATION);
    }
}
