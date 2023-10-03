package com.codesquad.secondhand.query.service;

import static com.codesquad.secondhand.common.utils.ChatMapper.toChatRoomDetail;

import com.codesquad.secondhand.command.domain.chat.ChatMessage;
import com.codesquad.secondhand.command.domain.chat.ChatRoom;
import com.codesquad.secondhand.command.domain.chat.dto.ChatRoomDto;
import com.codesquad.secondhand.common.utils.ChatMapper;
import com.codesquad.secondhand.query.controller.chat.response.ChatRoomDetail;
import com.codesquad.secondhand.query.controller.chat.response.ChatRoomInfo;
import com.codesquad.secondhand.query.port.ChatRoomQueryUseCase;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ChatRoomQueryFacade implements ChatRoomQueryUseCase {

    private final ChatRoomQueryService chatRoomQueryService;
    private final ChatMessageQueryService chatMessageQueryService;

    @Transactional(readOnly = true)
    @Override
    public ChatRoomDetail getChatRoomDetail(Long chatRoomId, Long memberId) {
        ChatRoom chatRoom = chatRoomQueryService.getById(chatRoomId);
        List<ChatMessage> chatMessages = chatMessageQueryService.getChatMessagesByChatRoomId(chatRoomId);
        return toChatRoomDetail(chatRoom, chatMessages, memberId);
    }

    @Override
    public List<ChatRoomInfo> getJoinedChatRooms(Long memberId) {
        List<ChatRoomDto> joinedChatRooms = chatRoomQueryService.getJoinedChatRooms(memberId);
        return toChatRoomInfos(joinedChatRooms);
    }

    private List<ChatRoomInfo> toChatRoomInfos(List<ChatRoomDto> joinedChatRooms) {
        return joinedChatRooms.stream()
                .map(ChatMapper::toChatRoomInfo)
                .collect(Collectors.toList());
    }
}
