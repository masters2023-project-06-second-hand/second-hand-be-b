package com.codesquad.secondhand.command.port.in;

import com.codesquad.secondhand.command.adapter.in.web.chat.response.ChatRoomId;

public interface ChatRoomCommandUseCase {

    /**
     * 채팅방의 ID를 조회하여 반환한다. 존재하지 않으면 채팅방을 새로 생성하고 생성한 채팅방 ID를 반환한다.
     *
     * @param productId 상품 식별자 ID
     * @param sellerId  판매자 식별자 ID
     * @param memberId  로그인 된 사용자 ID
     * @return 채팅방의 식별자 ID를 담은 객체
     */
    ChatRoomId getChatRoomId(long productId, long sellerId, long memberId);

    /**
     * 채팅방의 사용자 목록에 사용자를 추가한다.
     *
     * @param chatRoomId 채팅방 ID
     * @param memberId   추가할 사용자 ID
     */
    void addChatRoomMember(long chatRoomId, long memberId);

    /**
     * 채팅방의 사용자 목록에서 사용자를 삭제한다.
     *
     * @param chatRoomId 채팅방 ID
     * @param memberId   삭제할 사용자 ID
     */
    void deleteChatRoomMember(long chatRoomId, long memberId);
}
