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
     * @param member    로그인한 사용자
     * @return 채팅방의 식별자 ID를 담은 객체
     */
    ChatRoomId getChatRoomId(long productId, long sellerId, Member member);

    ChatRoomDetail getChatRoomDetail(long chatRoomId, Member member);
}
