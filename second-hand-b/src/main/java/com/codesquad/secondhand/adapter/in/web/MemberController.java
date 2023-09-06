package com.codesquad.secondhand.adapter.in.web;

import com.codesquad.secondhand.application.port.in.MemberUseCase;
import com.codesquad.secondhand.application.port.in.request.ToggleProductLikeStatusRequest;
import com.codesquad.secondhand.application.port.in.response.CategorySimpleDetail;
import com.codesquad.secondhand.application.port.in.response.ProductDetail;
import com.codesquad.secondhand.domain.member.Member;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
public class MemberController {

    private final MemberUseCase memberUseCase;

    @PutMapping("/api/products/{productId}/likes")
    public ResponseEntity<Void> toggleProductLikeStatus(
            @AuthenticationPrincipal Member member,
            @RequestBody ToggleProductLikeStatusRequest toggleProductLikeStatusRequest,
            @PathVariable Long productId
    ) {
        memberUseCase.toggleProductLikeStatus(member, productId, toggleProductLikeStatusRequest.isLiked());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/api/members/{memberId}/likes")
    public ResponseEntity<List<ProductDetail>> fetchMemberFavoriteProducts(
            @AuthenticationPrincipal Member member,
            @PathVariable long memberId,
            @RequestParam Optional<Long> categoryId
    ) {
        if (categoryId.isPresent()) {
            List<ProductDetail> productDetails = memberUseCase.fetchMemberFavoriteProducts(member, memberId,
                    categoryId.get());
            return ResponseEntity.ok().body(productDetails);
        }
        List<ProductDetail> productDetails = memberUseCase.fetchMemberFavoriteProducts(member, memberId);
        return ResponseEntity.ok().body(productDetails);
    }

    @GetMapping("/api/members/{memberId}/likes/categories")
    public ResponseEntity<List<CategorySimpleDetail>> fetchMemberInterestCategories(
            @AuthenticationPrincipal Member member,
            @PathVariable long memberId
    ) {
        List<CategorySimpleDetail> categories = memberUseCase.fetchMemberInterestCategories(member, memberId);
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/api/members/{memberId}/sales")
    public ResponseEntity<List<ProductDetail>> getMySellingProducts(
            @AuthenticationPrincipal Member member,
            @PathVariable long memberId
    ) {
        List<ProductDetail> categories = memberUseCase.getMySellingProducts(member, memberId);
        return ResponseEntity.ok(categories);
    }
}
