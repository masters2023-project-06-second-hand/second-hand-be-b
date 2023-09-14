package com.codesquad.secondhand.application.port.in;

import com.codesquad.secondhand.adapter.in.web.request.ProductCreateRequest;
import com.codesquad.secondhand.adapter.in.web.request.ProductModifyRequest;
import com.codesquad.secondhand.adapter.in.web.response.ProductDetail;
import com.codesquad.secondhand.adapter.in.web.response.ProductInfo;
import com.codesquad.secondhand.domain.member.Member;
import java.util.List;

public interface ProductUseCase {

    Long save(ProductCreateRequest productCreateRequest, Member email);

    ProductDetail getDetails(Long id);

    void modify(Long id, ProductModifyRequest productModifyRequest);

    void modifyStatus(Long id, String status);

    List<ProductInfo> getProductsByRegion(Long regionId);

    List<ProductInfo> getProductsByRegionAndCategory(Long regionId, Long categoryId);

    void delete(Long id);
}
