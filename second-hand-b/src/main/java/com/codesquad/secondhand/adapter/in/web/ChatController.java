package com.codesquad.secondhand.adapter.in.web;

import com.codesquad.secondhand.adapter.in.web.request.ChatRoomIdRequest;
import com.codesquad.secondhand.adapter.in.web.response.ChatRoomId;
import com.codesquad.secondhand.application.port.in.ChatUseCase;
import com.codesquad.secondhand.domain.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ChatController {

    private final ChatUseCase chatUseCase;

    @GetMapping("/api/chats/room-id")
    public ResponseEntity<ChatRoomId> getChatRoomId(
            @AuthenticationPrincipal Member member,
            @RequestBody ChatRoomIdRequest chatRoomIdRequest) {
        ChatRoomId chatRoomId = chatUseCase.getChatRoomId(chatRoomIdRequest.getProductId(),
                chatRoomIdRequest.getSellerId(), member);
        return ResponseEntity.ok(chatRoomId);
    }
}
