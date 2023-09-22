package com.codesquad.secondhand.command.adapter.out.persistence;

import com.codesquad.secondhand.command.adapter.out.persistence.imports.ChatRoomMemberCrudRepository;
import com.codesquad.secondhand.command.domain.chat.ChatRoomMember;
import com.codesquad.secondhand.command.port.out.ChatRoomMemberRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ChatRoomMemberRepositoryImpl implements ChatRoomMemberRepository {

    private final ChatRoomMemberCrudRepository chatRoomMemberCrudRepository;

    @Override
    public ChatRoomMember save(ChatRoomMember chatRoomMember) {
        return chatRoomMemberCrudRepository.save(chatRoomMember);
    }

    @Override
    public List<ChatRoomMember> findAllByChatRoomId(long chatRoomId) {
        return chatRoomMemberCrudRepository.findAllByChatRoomId(chatRoomId);
    }

    @Override
    public void deleteByChatRoomIdAndMemberId(long chatRoomId, long memberId) {
        chatRoomMemberCrudRepository.deleteByChatRoomIdAndMemberId(chatRoomId, memberId);
    }
}
