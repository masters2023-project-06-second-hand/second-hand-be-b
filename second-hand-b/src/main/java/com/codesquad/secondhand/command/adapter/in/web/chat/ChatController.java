package com.codesquad.secondhand.command.adapter.in.web.chat;

import com.codesquad.secondhand.command.adapter.in.web.chat.request.ChatRoomIdRequest;
import com.codesquad.secondhand.command.adapter.in.web.chat.response.ChatRoomDetail;
import com.codesquad.secondhand.command.adapter.in.web.chat.response.ChatRoomId;
import com.codesquad.secondhand.command.port.in.ChatUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ChatController {

    private final ChatUseCase chatUseCase;

    @GetMapping("/api/chats/room-id")
    public ResponseEntity<ChatRoomId> getChatRoomId(
            @AuthenticationPrincipal String memberId,
            @RequestBody ChatRoomIdRequest chatRoomIdRequest) {
        ChatRoomId chatRoomId = chatUseCase.getChatRoomId(chatRoomIdRequest.getProductId(),
                chatRoomIdRequest.getSellerId(), Long.parseLong(memberId));
        return ResponseEntity.ok(chatRoomId);
    }

    @GetMapping("/api/chats/{chatRoomId}")
    public ResponseEntity<ChatRoomDetail> getChatRoomDetail(
            @AuthenticationPrincipal String memberId,
            @PathVariable Long chatRoomId) {
        ChatRoomDetail chatRoomDetail = chatUseCase.getChatRoomDetail(chatRoomId, Long.parseLong(memberId));
        return ResponseEntity.ok(chatRoomDetail);
    }
}
