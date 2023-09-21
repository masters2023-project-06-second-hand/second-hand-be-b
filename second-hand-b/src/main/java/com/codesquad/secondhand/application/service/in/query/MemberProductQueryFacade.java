package com.codesquad.secondhand.application.service.in.query;

import static com.codesquad.secondhand.application.service.in.MemberUtils.validateMemberPermission;
import static com.codesquad.secondhand.application.service.in.common.utils.ProductMapper.toProductInfo;

import com.codesquad.secondhand.adapter.in.web.query.prodcut.response.ProductInfo;
import com.codesquad.secondhand.adapter.in.web.query.prodcut.response.ProductsInfo;
import com.codesquad.secondhand.application.port.in.query.MemberProductQueryUseCase;
import com.codesquad.secondhand.application.service.in.prodcut.ProductService;
import com.codesquad.secondhand.application.service.in.region.RegionService;
import com.codesquad.secondhand.domain.product.Product;
import com.codesquad.secondhand.domain.product.Status;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class MemberProductQueryFacade implements MemberProductQueryUseCase {

    private final ProductService productService;
    private final RegionService regionService;

    @Override
    public ProductsInfo getMySellingProductsByStatus(String validatedMemberId, long memberId, String statusName,
            Pageable pageable) {
        validateMemberPermission(validatedMemberId, memberId);
        Status status = Status.findByName(statusName);
        if (Status.isSoldOut(status)) {
            Slice<Product> products = productService.getSoldOutByWriterId(memberId, pageable);
            return toProductsInfo(products);
        }
        Slice<Product> products = productService.getSalesByWriterId(memberId, pageable);
        return toProductsInfo(products);
    }

    @Override
    public ProductsInfo getMySellingProducts(String validatedMemberId, long memberId, Pageable pageable) {
        validateMemberPermission(validatedMemberId, memberId);
        Slice<Product> products = productService.getByWriterId(memberId, pageable);
        return toProductsInfo(products);
    }

    @Override
    public ProductsInfo fetchMemberFavoriteProducts(String validatedMemberId, long memberId, Long categoryId,
            Pageable pageable) {
        validateMemberPermission(validatedMemberId, memberId);
        Slice<Product> products = productService.getLikesByMemberIdAndCategoryId(memberId, categoryId, pageable);
        return toProductsInfo(products);
    }

    @Override
    public ProductsInfo fetchMemberFavoriteProducts(String validatedMemberId, long memberId, Pageable pageable) {
        validateMemberPermission(validatedMemberId, memberId);
        Slice<Product> products = productService.getLikesByMemberId(memberId, pageable);
        return toProductsInfo(products);
    }

    private ProductsInfo toProductsInfo(Slice<Product> products) {
        List<ProductInfo> productInfoList = products.stream()
                .map(product -> toProductInfo(product, regionService.getById(product.getRegionId())))
                .collect(Collectors.toList());
        return new ProductsInfo(productInfoList, products.hasNext(), products.getNumber());
    }
}
