package com.codesquad.secondhand.adapter.in.web.query.prodcut;

import com.codesquad.secondhand.adapter.in.web.query.prodcut.response.ProductsInfo;
import com.codesquad.secondhand.application.port.in.query.MemberProductQueryUseCase;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
public class MemberProductQueryController {

    private final MemberProductQueryUseCase memberProductQueryUseCase;

    @GetMapping("/api/members/{memberId}/sales")
    public ResponseEntity<ProductsInfo> getMySellingProducts(
            @AuthenticationPrincipal String validatedMemberId,
            @RequestParam Optional<String> status,
            @PathVariable long memberId,
            Pageable pageable
    ) {
        if (status.isPresent()) {
            ProductsInfo productInfos = memberProductQueryUseCase.getMySellingProductsByStatus(validatedMemberId, memberId,
                    status.get(), pageable);
            return ResponseEntity.ok(productInfos);
        }
        ProductsInfo productInfos = memberProductQueryUseCase.getMySellingProducts(validatedMemberId, memberId, pageable);
        return ResponseEntity.ok(productInfos);
    }

    @GetMapping("/api/members/{memberId}/likes")
    public ResponseEntity<ProductsInfo> fetchMemberFavoriteProducts(
            @AuthenticationPrincipal String validatedMemberId,
            @PathVariable long memberId,
            @RequestParam Optional<Long> categoryId,
            Pageable pageable
    ) {
        if (categoryId.isPresent()) {
            ProductsInfo productInfos = memberProductQueryUseCase.fetchMemberFavoriteProducts(validatedMemberId, memberId,
                    categoryId.get(), pageable);
            return ResponseEntity.ok(productInfos);
        }
        ProductsInfo productDetails = memberProductQueryUseCase.fetchMemberFavoriteProducts(validatedMemberId, memberId, pageable);
        return ResponseEntity.ok(productDetails);
    }
}
