package com.codesquad.secondhand.application.port.in;

import com.codesquad.secondhand.application.port.in.response.ProductDetail;
import com.codesquad.secondhand.domain.member.Member;
import java.util.List;

public interface MemberUseCase {

    void toggleProductLikeStatus(Member member, Long productId, boolean isLiked);

    List<ProductDetail> fetchMemberFavoriteProducts(Member member, Long memberId, long categoryId);
}
