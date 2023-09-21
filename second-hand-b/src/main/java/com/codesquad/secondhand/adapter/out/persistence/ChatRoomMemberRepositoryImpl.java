package com.codesquad.secondhand.adapter.out.persistence;

import com.codesquad.secondhand.adapter.out.persistence.imports.ChatRoomMemberCrudRepository;
import com.codesquad.secondhand.application.port.out.ChatRoomMemberRepository;
import com.codesquad.secondhand.domain.chat.ChatRoomMember;
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
}
