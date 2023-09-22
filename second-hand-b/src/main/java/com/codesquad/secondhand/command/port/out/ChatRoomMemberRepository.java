package com.codesquad.secondhand.command.port.out;

import com.codesquad.secondhand.command.domain.chat.ChatRoomMember;
import java.util.List;

public interface ChatRoomMemberRepository {

    ChatRoomMember save(ChatRoomMember chatRoomMember);

    List<ChatRoomMember> findAllByChatRoomId(long chatRoomId);

    void deleteByChatRoomIdAndMemberId(long chatRoomId, long memberId);
}
