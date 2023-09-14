package com.codesquad.secondhand.application.service.in;

import com.codesquad.secondhand.adapter.in.web.request.SignUpRequest;
import com.codesquad.secondhand.adapter.in.web.response.CategorySimpleDetail;
import com.codesquad.secondhand.adapter.in.web.response.MemberInfo;
import com.codesquad.secondhand.adapter.in.web.response.ProductInfo;
import com.codesquad.secondhand.application.port.in.MemberUseCase;
import com.codesquad.secondhand.application.port.out.MemberRepository;
import com.codesquad.secondhand.application.service.in.exception.MemberNotFoundException;
import com.codesquad.secondhand.application.service.in.exception.PermissionDeniedException;
import com.codesquad.secondhand.application.service.in.prodcut.ProductService;
import com.codesquad.secondhand.application.service.in.region.RegionService;
import com.codesquad.secondhand.domain.member.Member;
import com.codesquad.secondhand.domain.member.Role;
import com.codesquad.secondhand.domain.product.Product;
import com.codesquad.secondhand.domain.product.Status;
import com.codesquad.secondhand.domain.region.Region;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MemberService implements MemberUseCase {

    private static final int REGIONS_FIRST_INDEX = 0;
    private static final int REGIONS_SECOND_INDEX = 1;
    private static final int SIZE_2 = 2;

    private final MemberRepository memberRepository;
    private final RegionService regionService;
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

    @Override
    public MemberInfo getProfile(Member member, Long memberId) {
        validateMemberPermission(member, memberId);
        return new MemberInfo(member.getId(), member.getNickname(), member.getProfileImage());
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
        return productService.getProductsByMemberId(memberId);
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

    public Member getById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(MemberNotFoundException::new);
    }

    public Member getByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(MemberNotFoundException::new);
    }

    public Member signUpMember(String email, SignUpRequest signUpRequest) {
        Member member = new Member(
                email,
                signUpRequest.getNickname(),
                signUpRequest.getProfileImg(),
                Role.MEMBER
        );
        addRegions(signUpRequest.getRegionsId(), member);
        return save(member);
    }

    private void addRegions(List<Long> regionsId, Member member) {
        Region firstRegion = regionService.getById(regionsId.get(REGIONS_FIRST_INDEX));
        member.addRegion(firstRegion);
        member.selectRegion(firstRegion);

        if (regionsId.size() == SIZE_2) {
            Region secondRegion = regionService.getById(regionsId.get(REGIONS_SECOND_INDEX));
            member.addRegion(secondRegion);
        }
    }
}
