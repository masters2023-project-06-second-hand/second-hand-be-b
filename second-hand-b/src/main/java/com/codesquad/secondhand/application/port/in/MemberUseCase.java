package com.codesquad.secondhand.application.port.in;

import com.codesquad.secondhand.adapter.in.web.response.CategorySimpleDetail;
import com.codesquad.secondhand.adapter.in.web.response.MemberInfo;
import com.codesquad.secondhand.adapter.in.web.response.ProductInfo;
import com.codesquad.secondhand.domain.member.Member;
import java.util.List;

public interface MemberUseCase {

    MemberInfo getProfile(Member member, Long memberId);

    void toggleProductLikeStatus(Member member, long productId, boolean isLiked);

    List<ProductInfo> fetchMemberFavoriteProducts(Member member, long memberId);

    List<ProductInfo> fetchMemberFavoriteProducts(Member member, long memberId, long categoryId);

    List<CategorySimpleDetail> fetchMemberInterestCategories(Member member, long memberId);

    List<ProductInfo> getMySellingProducts(Member member, long memberId);

    List<ProductInfo> getMySellingProductsByStatus(Member member, long memberId, String statusName);

    Member getByEmail(String email);
}
