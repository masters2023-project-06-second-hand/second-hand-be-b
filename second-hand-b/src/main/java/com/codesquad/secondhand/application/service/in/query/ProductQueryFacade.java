package com.codesquad.secondhand.application.service.in.query;

import static com.codesquad.secondhand.application.service.in.common.utils.ProductMapper.toProductDetail;
import static com.codesquad.secondhand.application.service.in.common.utils.ProductMapper.toProductInfo;

import com.codesquad.secondhand.adapter.in.web.query.prodcut.response.ProductDetail;
import com.codesquad.secondhand.adapter.in.web.query.prodcut.response.ProductInfo;
import com.codesquad.secondhand.adapter.in.web.query.prodcut.response.ProductsInfo;
import com.codesquad.secondhand.application.port.in.query.ProductQueryUseCase;
import com.codesquad.secondhand.domain.member.Member;
import com.codesquad.secondhand.domain.product.Category;
import com.codesquad.secondhand.domain.product.Product;
import com.codesquad.secondhand.domain.region.Region;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
public class ProductQueryFacade implements ProductQueryUseCase {

    private final ProductQueryService productQueryService;
    private final RegionQueryService regionQueryService;
    private final MemberQueryService memberQueryService;

    @Transactional
    @Override
    public ProductDetail getDetails(long id) {
        Product product = productQueryService.getById(id);
        long writerId = product.getWriterId();
        Member member = memberQueryService.getById(writerId);
        Category category = product.getCategory();
        long regionId = product.getRegionId();
        Region region = regionQueryService.getById(regionId);
        return toProductDetail(product, category, member, region);
    }

    @Transactional(readOnly = true)
    @Override
    public ProductsInfo getProductsByRegion(long regionId, Pageable pageable) {
        Slice<Product> products = productQueryService.getProductsByRegion(regionId, pageable);
        return toProductsInfo(products);
    }

    @Transactional(readOnly = true)
    @Override
    public ProductsInfo getProductsByRegionAndCategory(long regionId, long categoryId, Pageable pageable) {
        Slice<Product> products = productQueryService.getProductsByRegionAndCategory(regionId, categoryId, pageable);
        return toProductsInfo(products);
    }

    private ProductsInfo toProductsInfo(Slice<Product> products) {
        List<ProductInfo> productInfoList = products.stream()
                .map(product -> toProductInfo(product, regionQueryService.getById(product.getRegionId())))
                .collect(Collectors.toList());
        return new ProductsInfo(productInfoList, products.hasNext(), products.getNumber());
    }
}
