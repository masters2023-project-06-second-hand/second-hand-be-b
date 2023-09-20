package com.codesquad.secondhand.application.service.in;

import static com.codesquad.secondhand.application.service.in.MemberUtils.validateMemberPermission;
import static com.codesquad.secondhand.application.service.in.prodcut.ProductMapper.toProductInfo;

import com.codesquad.secondhand.adapter.in.web.response.CategorySimpleDetail;
import com.codesquad.secondhand.adapter.in.web.response.MemberInfo;
import com.codesquad.secondhand.adapter.in.web.response.product.ProductInfo;
import com.codesquad.secondhand.application.port.in.MemberUseCase;
import com.codesquad.secondhand.application.service.in.prodcut.ProductService;
import com.codesquad.secondhand.application.service.in.region.RegionService;
import com.codesquad.secondhand.domain.member.Member;
import com.codesquad.secondhand.domain.product.Product;
import com.codesquad.secondhand.domain.product.Status;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
public class MemberFacade implements MemberUseCase {

    private final RegionService regionService;
    private final ProductService productService;
    private final CategoryService categoryService;
    private final MemberService memberService;


    @Transactional(readOnly = true)
    @Override
    public MemberInfo getProfile(Member member, Long memberId) {
        return memberService.getProfile(member, memberId);
    }

    @Transactional
    @Override
    public void toggleProductLikeStatus(Member member, long productId, boolean isLiked) {
        Product product = productService.getById(productId);
        memberService.toggleProductLikeStatus(member, isLiked, product);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ProductInfo> fetchMemberFavoriteProducts(Member member, long memberId) {
        validateMemberPermission(member, memberId);
        List<Product> products = productService.getLikesByMemberId(memberId);
        return toProductsInfo(products);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ProductInfo> fetchMemberFavoriteProducts(Member member, long memberId, long categoryId) {
        validateMemberPermission(member, memberId);
        List<Product> products = productService.getLikesByMemberIdAndCategoryId(memberId, categoryId);
        return toProductsInfo(products);
    }

    @Transactional(readOnly = true)
    @Override
    public List<CategorySimpleDetail> fetchMemberInterestCategories(Member member, long memberId) {
        validateMemberPermission(member, memberId);
        return categoryService.getCategoryByMemberId(memberId);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ProductInfo> getMySellingProducts(Member member, long memberId) {
        validateMemberPermission(member, memberId);
        List<Product> products = productService.getByWriterId(memberId);
        return toProductsInfo(products);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ProductInfo> getMySellingProductsByStatus(Member member, long memberId, String statusName) {
        validateMemberPermission(member, memberId);
        Status status = Status.findByName(statusName);
        if (Status.isSoldOut(status)) {
            List<Product> products = productService.getSoldOutByWriterId(memberId);
            return toProductsInfo(products);
        }
        List<Product> products = productService.getSalesByWriterId(memberId);
        return toProductsInfo(products);
    }

    @Override
    public Member getByEmail(String email) {
        return memberService.getByEmail(email);
    }

    private List<ProductInfo> toProductsInfo(List<Product> products) {
        return products.stream()
                .map(product -> toProductInfo(product, regionService.getById(product.getRegionId())))
                .collect(Collectors.toList());
    }
}
