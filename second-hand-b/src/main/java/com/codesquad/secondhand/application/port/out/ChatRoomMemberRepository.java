package com.codesquad.secondhand.application.port.out;

import com.codesquad.secondhand.domain.chat.ChatRoomMember;

public interface ChatRoomMemberRepository {

    ChatRoomMember save(ChatRoomMember chatRoomMember);
}
