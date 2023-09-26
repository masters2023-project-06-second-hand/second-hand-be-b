package com.codesquad.secondhand.query.port;

import com.codesquad.secondhand.query.controller.chat.response.ChatRoomDetail;

public interface ChatRoomQueryUseCase {

    /**
     * 채팅방 ID로 채팅방의 정보를 조회하여 반환한다.
     *
     * @param chatRoomId 채팅방 ID
     * @param memberId   로그인 된 사용자 ID
     * @return 채팅 정보와 상품 정보를 담은 객체
     */
    ChatRoomDetail getChatRoomDetail(Long chatRoomId, Long memberId);
}
