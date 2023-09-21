package com.codesquad.secondhand.application.port.in.query;

import com.codesquad.secondhand.adapter.in.web.query.prodcut.response.ProductsInfo;
import org.springframework.data.domain.Pageable;

public interface MemberProductQueryUseCase {

    ProductsInfo getMySellingProductsByStatus(String validatedMemberId, long memberId, String statusName, Pageable pageable);

    ProductsInfo getMySellingProducts(String validatedMemberId, long memberId, Pageable pageable);

    ProductsInfo fetchMemberFavoriteProducts(String validatedMemberId, long memberId, Long categoryId, Pageable pageable);

    ProductsInfo fetchMemberFavoriteProducts(String validatedMemberId, long memberId, Pageable pageable);
}
