package com.codesquad.secondhand.query.port;

import com.codesquad.secondhand.query.controller.chat.response.ChatRoomDetail;
import com.codesquad.secondhand.query.controller.chat.response.ChatRoomInfo;
import java.util.List;

public interface ChatRoomQueryUseCase {

    /**
     * 채팅방 ID로 채팅방의 정보를 조회하여 반환한다.
     *
     * @param chatRoomId 채팅방 ID
     * @param memberId   로그인 된 사용자 ID
     * @return 채팅 정보와 상품 정보를 담은 객체
     */
    ChatRoomDetail getChatRoomDetail(Long chatRoomId, Long memberId);

    /**
     * 사용자가 참여하고 있는 채팅방의 목록을 조회한다.
     *
     * @param memberId 사용자 ID
     * @return 채팅방 정보 목록을 담은 객체
     */
    List<ChatRoomInfo> getJoinedChatRooms(Long memberId);
}
