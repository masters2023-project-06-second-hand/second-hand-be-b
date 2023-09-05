package com.codesquad.secondhand.application.port.in;

import com.codesquad.secondhand.domain.member.Member;

public interface MemberUseCase {

    void toggleProductLikeStatus(Member member, Long productId, boolean isLiked);
}
