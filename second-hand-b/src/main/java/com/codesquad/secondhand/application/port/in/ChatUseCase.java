package com.codesquad.secondhand.application.port.in;

import com.codesquad.secondhand.adapter.in.web.response.ChatRoomDetail;
import com.codesquad.secondhand.adapter.in.web.response.ChatRoomId;
import com.codesquad.secondhand.domain.member.Member;

public interface ChatUseCase {

    /**
     * 채팅방의 ID를 조회하여 반환한다. 채팅방이 존재하지 않으면 새로 생성하고 생성한 ID를 반환한다.
     *
     * @param productId 상품 식별자 ID
     * @param sellerId  판매자 식별자 ID
     * @param member    로그인 된 사용자
     * @return 채팅방의 식별자 ID를 담은 객체
     */
    ChatRoomId getChatRoomId(long productId, long sellerId, Member member);

    /**
     * 채팅방 ID로 채팅방의 정보를 조회하여 반환한다.
     *
     * @param chatRoomId 채팅방 ID
     * @param member     로그인 된 사용자
     * @return 채팅 정보와 상품 정보를 담은 객체
     */
    ChatRoomDetail getChatRoomDetail(long chatRoomId, Member member);

    /**
     * 채팅방 ID에 존재하는 메시지 중 상대방이 보낸 메시지를 모두 읽음 처리한다.
     *
     * @param chatRoomId 채팅방 ID
     * @param memberId   로그인 된 사용자 ID
     */
    void markMessagesAsRead(long chatRoomId, long memberId);

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

    /**
     * 채팅방에서 보낸 메시지를 저장한다.
     *
     * @param chatRoomId 채팅방 ID
     * @param message    메시지
     * @param senderId   보낸 사용자 ID
     */
    void saveChatMessage(long chatRoomId, String message, long senderId);
}
