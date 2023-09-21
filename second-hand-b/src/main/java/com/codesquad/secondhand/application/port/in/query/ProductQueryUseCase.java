package com.codesquad.secondhand.application.port.in.query;

import com.codesquad.secondhand.adapter.in.web.query.prodcut.response.ProductDetail;
import com.codesquad.secondhand.adapter.in.web.query.prodcut.response.ProductsInfo;
import org.springframework.data.domain.Pageable;

public interface ProductQueryUseCase {

    ProductDetail getDetails(long id);

    ProductsInfo getProductsByRegion(long regionId, Pageable pageable);

    ProductsInfo getProductsByRegionAndCategory(long regionId, long categoryId, Pageable pageable);

}
