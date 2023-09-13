package com.codesquad.secondhand.application.port.in;

import com.codesquad.secondhand.adapter.in.web.request.ProductCreateRequest;
import com.codesquad.secondhand.adapter.in.web.request.ProductModifyRequest;
import com.codesquad.secondhand.adapter.in.web.response.ProductDetail;
import com.codesquad.secondhand.adapter.in.web.response.ProductInfo;
import com.codesquad.secondhand.domain.member.Member;
import java.util.List;

public interface ProductUseCase {

    long save(ProductCreateRequest productCreateRequest, Member email);

    ProductDetail getDetails(long id);

    void modify(long id, ProductModifyRequest productModifyRequest);

    void modifyStatus(long id, String status);

    List<ProductInfo> getProductsByRegion(long regionId);

    List<ProductInfo> getProductsByRegionAndCategory(long regionId, long categoryId);

    void delete(long id);
}
