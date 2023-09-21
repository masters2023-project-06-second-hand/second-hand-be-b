package com.codesquad.secondhand.application.port.in;

import com.codesquad.secondhand.adapter.in.web.request.product.ProductCreateRequest;
import com.codesquad.secondhand.adapter.in.web.request.product.ProductModifyRequest;
import com.codesquad.secondhand.adapter.in.web.response.product.ProductDetail;
import com.codesquad.secondhand.adapter.in.web.response.product.ProductsInfo;
import com.codesquad.secondhand.domain.member.Member;
import org.springframework.data.domain.Pageable;

public interface ProductUseCase {

    long save(ProductCreateRequest productCreateRequest, Member email);

    ProductDetail getDetails(long id);

    void modify(long id, ProductModifyRequest productModifyRequest);

    void modifyStatus(long id, String status);

    ProductsInfo getProductsByRegion(long regionId, Pageable pageable);

    ProductsInfo getProductsByRegionAndCategory(long regionId, long categoryId, Pageable pageable);

    void delete(long id);

    ProductsInfo getMySellingProductsByStatus(Member member, long memberId, String statusName, Pageable pageable);

    ProductsInfo getMySellingProducts(Member member, long memberId, Pageable pageable);

    void toggleProductLikeStatus(Member member, Long productId, boolean liked);


    ProductsInfo fetchMemberFavoriteProducts(Member member, long memberId, Long categoryId, Pageable pageable);

    ProductsInfo fetchMemberFavoriteProducts(Member member, long memberId, Pageable pageable);
}
