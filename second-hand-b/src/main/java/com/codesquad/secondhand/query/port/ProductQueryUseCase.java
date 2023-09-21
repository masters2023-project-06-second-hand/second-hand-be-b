package com.codesquad.secondhand.query.port;

import com.codesquad.secondhand.query.controller.prodcut.response.ProductDetail;
import com.codesquad.secondhand.query.controller.prodcut.response.ProductsInfo;
import org.springframework.data.domain.Pageable;

public interface ProductQueryUseCase {

    ProductDetail getDetails(long id);

    ProductsInfo getProductsByRegion(long regionId, Pageable pageable);

    ProductsInfo getProductsByRegionAndCategory(long regionId, long categoryId, Pageable pageable);

}
