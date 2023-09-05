package com.codesquad.secondhand.application.service.in;

import com.codesquad.secondhand.application.port.in.MemberUseCase;
import com.codesquad.secondhand.application.port.in.exception.MemberNotFoundException;
import com.codesquad.secondhand.application.port.in.exception.PermissionDeniedException;
import com.codesquad.secondhand.application.port.in.response.ProductDetail;
import com.codesquad.secondhand.application.port.out.MemberRepository;
import com.codesquad.secondhand.domain.member.Member;
import com.codesquad.secondhand.domain.product.Product;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MemberService implements MemberUseCase {

    private final MemberRepository memberRepository;
    private final ProductService productService;

    public Member save(Member member) {
        return memberRepository.save(member);
    }

    public Member findByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(MemberNotFoundException::new);
    }

    public Member getById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(MemberNotFoundException::new);
    }

    @Transactional
    @Override
    public void toggleProductLikeStatus(Member member, Long productId, boolean isLiked) {
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
    public List<ProductDetail> fetchMemberFavoriteProducts(Member member, Long memberId, long categoryId) {
        if (!member.getId().equals(memberId)) {
            throw new PermissionDeniedException();
        }
        Member savedMember = getById(memberId);
        Set<Product> products = savedMember.getProducts();
        return productService.toProductDetails(products);
    }
}
