package com.codesquad.secondhand.common.messaging.controller;

import com.codesquad.secondhand.command.adapter.in.web.chat.request.ChatMessageRequest;
import com.codesquad.secondhand.command.port.in.ChatMessageCommandUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
public class ChatStompController {

    private final ChatMessageCommandUseCase chatMessageCommandUseCase;
    private final SimpMessagingTemplate template;

    @MessageMapping("/message")
    public void sendMessage(ChatMessageRequest chatMessageRequest) {
        chatMessageCommandUseCase.saveChatMessage(chatMessageRequest.getChatRoomId(),
                chatMessageRequest.getMessage(),
                chatMessageRequest.getSenderId());
        template.convertAndSend("/sub/room/" + chatMessageRequest.getChatRoomId(), chatMessageRequest);
    }
}
