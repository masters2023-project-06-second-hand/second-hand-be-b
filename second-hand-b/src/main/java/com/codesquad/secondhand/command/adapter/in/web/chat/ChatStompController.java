package com.codesquad.secondhand.command.adapter.in.web.chat;

import static com.codesquad.secondhand.common.utils.ChatMapper.toChatMessageResponse;

import com.codesquad.secondhand.command.adapter.in.web.chat.request.ChatMessageRequest;
import com.codesquad.secondhand.command.adapter.in.web.chat.response.ChatMessageResponse;
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

        ChatMessageResponse chatMessageResponse = toChatMessageResponse(chatMessageRequest);
        template.convertAndSend("/sub/room/" + chatMessageRequest.getChatRoomId(),
                chatMessageResponse);
    }
}
