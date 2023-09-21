package com.codesquad.secondhand.adapter.in.web.controller;

import com.codesquad.secondhand.adapter.in.web.request.product.ToggleProductLikeStatusRequest;
import com.codesquad.secondhand.adapter.in.web.response.product.ProductsInfo;
import com.codesquad.secondhand.application.port.in.ProductUseCase;
import com.codesquad.secondhand.domain.member.Member;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
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
public class MemberProductController {

    private final ProductUseCase productUseCase;

    @GetMapping("/api/members/{memberId}/sales")
    public ResponseEntity<ProductsInfo> getMySellingProducts(
            @AuthenticationPrincipal Member member,
            @RequestParam Optional<String> status,
            @PathVariable long memberId,
            Pageable pageable
    ) {
        if (status.isPresent()) {
            ProductsInfo productInfos = productUseCase.getMySellingProductsByStatus(member, memberId, status.get(),pageable);
            return ResponseEntity.ok(productInfos);
        }
        ProductsInfo productInfos = productUseCase.getMySellingProducts(member, memberId,pageable);
        return ResponseEntity.ok(productInfos);
    }

    @GetMapping("/api/members/{memberId}/likes")
    public ResponseEntity<ProductsInfo> fetchMemberFavoriteProducts(
            @AuthenticationPrincipal Member member,
            @PathVariable long memberId,
            @RequestParam Optional<Long> categoryId,
            Pageable pageable
    ) {
        if (categoryId.isPresent()) {
            ProductsInfo productInfos = productUseCase.fetchMemberFavoriteProducts(member, memberId,
                    categoryId.get(),pageable);
            return ResponseEntity.ok(productInfos);
        }
        ProductsInfo productDetails = productUseCase.fetchMemberFavoriteProducts(member, memberId,pageable);
        return ResponseEntity.ok(productDetails);
    }

    @PutMapping("/api/products/{productId}/likes")
    public ResponseEntity<Void> toggleProductLikeStatus(
            @AuthenticationPrincipal Member member,
            @RequestBody ToggleProductLikeStatusRequest toggleProductLikeStatusRequest,
            @PathVariable Long productId
    ) {
        productUseCase.toggleProductLikeStatus(member, productId, toggleProductLikeStatusRequest.isLiked());
        return ResponseEntity.noContent().build();
    }

}
