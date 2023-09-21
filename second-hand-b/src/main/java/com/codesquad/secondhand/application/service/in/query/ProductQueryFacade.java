package com.codesquad.secondhand.application.service.in.query;

import static com.codesquad.secondhand.application.service.in.common.utils.ProductMapper.toProductDetail;
import static com.codesquad.secondhand.application.service.in.common.utils.ProductMapper.toProductInfo;

import com.codesquad.secondhand.adapter.in.web.query.prodcut.response.ProductDetail;
import com.codesquad.secondhand.adapter.in.web.query.prodcut.response.ProductInfo;
import com.codesquad.secondhand.adapter.in.web.query.prodcut.response.ProductsInfo;
import com.codesquad.secondhand.application.port.in.query.ProductQueryUseCase;
import com.codesquad.secondhand.application.service.in.MemberService;
import com.codesquad.secondhand.application.service.in.prodcut.ProductService;
import com.codesquad.secondhand.application.service.in.region.RegionService;
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

    private final ProductService productService;
    private final RegionService regionService;
    private final MemberService memberService;

    @Transactional
    @Override
    public ProductDetail getDetails(long id) {
        Product product = productService.getById(id);
        long writerId = product.getWriterId();
        Member member = memberService.getById(writerId);
        Category category = product.getCategory();
        long regionId = product.getRegionId();
        Region region = regionService.getById(regionId);
        return toProductDetail(product, category, member, region);
    }

    @Transactional(readOnly = true)
    @Override
    public ProductsInfo getProductsByRegion(long regionId, Pageable pageable) {
        Slice<Product> products = productService.getProductsByRegion(regionId, pageable);
        return toProductsInfo(products);
    }

    @Transactional(readOnly = true)
    @Override
    public ProductsInfo getProductsByRegionAndCategory(long regionId, long categoryId, Pageable pageable) {
        Slice<Product> products = productService.getProductsByRegionAndCategory(regionId, categoryId, pageable);
        return toProductsInfo(products);
    }

    private ProductsInfo toProductsInfo(Slice<Product> products) {
        List<ProductInfo> productInfoList = products.stream()
                .map(product -> toProductInfo(product, regionService.getById(product.getRegionId())))
                .collect(Collectors.toList());
        return new ProductsInfo(productInfoList, products.hasNext(), products.getNumber());
    }
}
