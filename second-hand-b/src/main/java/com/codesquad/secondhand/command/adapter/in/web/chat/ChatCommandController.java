package com.codesquad.secondhand.command.adapter.in.web.chat;

import com.codesquad.secondhand.command.adapter.in.web.chat.request.ChatRoomIdRequest;
import com.codesquad.secondhand.command.adapter.in.web.chat.response.ChatRoomId;
import com.codesquad.secondhand.command.port.in.ChatRoomCommandUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ChatCommandController {

    private final ChatRoomCommandUseCase chatRoomCommandUseCase;

    @PostMapping("/api/chats/room-id")
    public ResponseEntity<ChatRoomId> getChatRoomId(
            @AuthenticationPrincipal String memberId,
            @RequestBody ChatRoomIdRequest chatRoomIdRequest) {
        ChatRoomId chatRoomId = chatRoomCommandUseCase.getOrCreateChatRoomId(chatRoomIdRequest.getProductId(),
                chatRoomIdRequest.getSellerId(), Long.parseLong(memberId));
        return ResponseEntity.ok(chatRoomId);
    }
}
