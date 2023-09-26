package com.codesquad.secondhand.query.controller.chat;

import com.codesquad.secondhand.query.controller.chat.response.ChatRoomDetail;
import com.codesquad.secondhand.query.port.ChatRoomQueryUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ChatQueryController {

    private final ChatRoomQueryUseCase chatRoomQueryUseCase;

    @GetMapping("/api/chats/{chatRoomId}")
    public ResponseEntity<ChatRoomDetail> getChatRoomDetail(
            @AuthenticationPrincipal String memberId,
            @PathVariable Long chatRoomId) {
        ChatRoomDetail chatRoomDetail = chatRoomQueryUseCase.getChatRoomDetail(chatRoomId, Long.parseLong(memberId));
        return ResponseEntity.ok(chatRoomDetail);
    }
}
