package com.codesquad.secondhand.adapter.in.web;

import com.codesquad.secondhand.adapter.in.web.request.ChatMessageRequest;
import com.codesquad.secondhand.application.port.in.ChatUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
public class ChatStompController {

    private final ChatUseCase chatUseCase;
    private final SimpMessagingTemplate template;

    @MessageMapping("/message")
    public void sendMessage(ChatMessageRequest chatMessageRequest) {
        chatUseCase.saveChatMessage(chatMessageRequest.getChatRoomId(),
                chatMessageRequest.getMessage(),
                chatMessageRequest.getSenderId());
        template.convertAndSend("/sub/room/" + chatMessageRequest.getChatRoomId(), chatMessageRequest);
    }
}
