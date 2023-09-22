package com.codesquad.secondhand.query.service;

import static com.codesquad.secondhand.common.utils.MemberUtils.validateMemberPermission;
import static com.codesquad.secondhand.common.utils.ProductMapper.toProductInfo;

import com.codesquad.secondhand.query.controller.prodcut.response.ProductInfo;
import com.codesquad.secondhand.query.controller.prodcut.response.ProductsInfo;
import com.codesquad.secondhand.query.port.MemberProductQueryUseCase;
import com.codesquad.secondhand.command.domain.product.Product;
import com.codesquad.secondhand.command.domain.product.Status;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class MemberProductQueryFacade implements MemberProductQueryUseCase {

    private final ProductQueryService productQueryService;
    private final RegionQueryService regionQueryService;

    @Override
    public ProductsInfo getMySellingProductsByStatus(String validatedMemberId, long memberId, String statusName,
            Pageable pageable) {
        validateMemberPermission(validatedMemberId, memberId);
        Status status = Status.findByName(statusName);
        if (Status.isSoldOut(status)) {
            Slice<Product> products = productQueryService.getSoldOutByWriterId(memberId, pageable);
            return toProductsInfo(products);
        }
        Slice<Product> products = productQueryService.getSalesByWriterId(memberId, pageable);
        return toProductsInfo(products);
    }

    @Override
    public ProductsInfo getMySellingProducts(String validatedMemberId, long memberId, Pageable pageable) {
        validateMemberPermission(validatedMemberId, memberId);
        Slice<Product> products = productQueryService.getByWriterId(memberId, pageable);
        return toProductsInfo(products);
    }

    @Override
    public ProductsInfo fetchMemberFavoriteProducts(String validatedMemberId, long memberId, Long categoryId,
            Pageable pageable) {
        validateMemberPermission(validatedMemberId, memberId);
        Slice<Product> products = productQueryService.getLikesByMemberIdAndCategoryId(memberId, categoryId, pageable);
        return toProductsInfo(products);
    }

    @Override
    public ProductsInfo fetchMemberFavoriteProducts(String validatedMemberId, long memberId, Pageable pageable) {
        validateMemberPermission(validatedMemberId, memberId);
        Slice<Product> products = productQueryService.getLikesByMemberId(memberId, pageable);
        return toProductsInfo(products);
    }

    private ProductsInfo toProductsInfo(Slice<Product> products) {
        List<ProductInfo> productInfoList = products.stream()
                .map(product -> toProductInfo(product, regionQueryService.getById(product.getRegionId())))
                .collect(Collectors.toList());
        return new ProductsInfo(productInfoList, products.hasNext(), products.getNumber());
    }
}
