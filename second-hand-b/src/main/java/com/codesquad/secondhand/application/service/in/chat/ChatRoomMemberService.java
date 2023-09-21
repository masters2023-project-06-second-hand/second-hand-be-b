package com.codesquad.secondhand.application.service.in.chat;

import com.codesquad.secondhand.application.port.out.ChatRoomMemberRepository;
import com.codesquad.secondhand.domain.chat.ChatRoomMember;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ChatRoomMemberService {

    private final ChatRoomMemberRepository chatRoomMemberRepository;

    public ChatRoomMember save(ChatRoomMember chatRoomMember) {
        return chatRoomMemberRepository.save(chatRoomMember);
    }

    public void deleteByChatRoomIdAndMemberId(long chatRoomId, long memberId) {
        chatRoomMemberRepository.deleteByChatRoomIdAndMemberId(chatRoomId, memberId);
    }
}
