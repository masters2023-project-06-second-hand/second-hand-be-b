package com.codesquad.secondhand.application.service.in;

import com.codesquad.secondhand.application.port.in.MemberUseCase;
import com.codesquad.secondhand.application.port.in.exception.MemberNotFoundException;
import com.codesquad.secondhand.application.port.in.exception.PermissionDeniedException;
import com.codesquad.secondhand.application.port.in.response.CategorySimpleDetail;
import com.codesquad.secondhand.application.port.in.response.ProductInfo;
import com.codesquad.secondhand.application.port.out.MemberRepository;
import com.codesquad.secondhand.domain.member.Member;
import com.codesquad.secondhand.domain.product.Product;
import com.codesquad.secondhand.domain.product.Status;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MemberService implements MemberUseCase {

    private final MemberRepository memberRepository;
    private final ProductService productService;
    private final CategoryService categoryService;

    public Member save(Member member) {
        return memberRepository.save(member);
    }

    public static void validateMemberPermission(Member member, long memberId) {
        if (!member.isSameId(memberId)) {
            throw new PermissionDeniedException();
        }
    }

    public Member getById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(MemberNotFoundException::new);
    }

    @Transactional
    @Override
    public void toggleProductLikeStatus(Member member, long productId, boolean isLiked) {
        Product product = productService.getById(productId);
        Member savedMember = getById(member.getId());
        if (isLiked) {
            savedMember.addLikes(product);
            return;
        }
        savedMember.removeLikes(product);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ProductInfo> fetchMemberFavoriteProducts(Member member, long memberId) {
        validateMemberPermission(member, memberId);
        Member savedMember = getById(memberId);
        List<Product> products = savedMember.getProducts();
        return productService.toProductInfos(products);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ProductInfo> fetchMemberFavoriteProducts(Member member, long memberId, long categoryId) {
        validateMemberPermission(member, memberId);
        return productService.getProductsByMemberIdAndCategoryId(memberId,
                categoryId);
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
        return productService.getByWriterId(memberId);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ProductInfo> getMySellingProductsByStatus(Member member, long memberId, String statusName) {
        validateMemberPermission(member, memberId);
        Status status = Status.findByName(statusName);
        if (Status.isSoldOut(status)) {
            return productService.getSoldOutByWriterId(memberId);
        }
        return productService.getSalesByWriterId(memberId);
    }

    public Member getByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(MemberNotFoundException::new);
    }
}
