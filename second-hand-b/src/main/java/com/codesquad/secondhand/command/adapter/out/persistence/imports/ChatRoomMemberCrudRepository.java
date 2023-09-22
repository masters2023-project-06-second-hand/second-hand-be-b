package com.codesquad.secondhand.command.adapter.out.persistence.imports;

import com.codesquad.secondhand.command.domain.chat.ChatRoomMember;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface ChatRoomMemberCrudRepository extends CrudRepository<ChatRoomMember, Long> {

    void deleteByChatRoomIdAndMemberId(long chatRoomId, long memberId);

    List<ChatRoomMember> findAllByChatRoomId(long chatRoomId);
}
