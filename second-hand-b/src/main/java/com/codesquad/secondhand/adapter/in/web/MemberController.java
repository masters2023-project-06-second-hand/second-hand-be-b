package com.codesquad.secondhand.adapter.in.web;

import com.codesquad.secondhand.application.port.in.MemberUseCase;
import com.codesquad.secondhand.application.port.in.request.ToggleProductLikeStatusRequest;
import com.codesquad.secondhand.application.port.in.response.ProductDetail;
import com.codesquad.secondhand.domain.member.Member;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RequiredArgsConstructor
@Controller
public class MemberController {

    private final MemberUseCase memberUseCase;

    @PutMapping("/api/products/{productId}/likes")
    public ResponseEntity<Void> toggleProductLikeStatus(@AuthenticationPrincipal Member member,
            @RequestBody ToggleProductLikeStatusRequest toggleProductLikeStatusRequest, @PathVariable Long productId) {
        memberUseCase.toggleProductLikeStatus(member, productId, toggleProductLikeStatusRequest.isLiked());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/api/members/{memberId}/likes")
    public ResponseEntity<List<ProductDetail>> fetchMemberFavoriteProducts(@AuthenticationPrincipal Member member,
            @PathVariable Long memberId) {
        List<ProductDetail> productDetails = memberUseCase.fetchMemberFavoriteProducts(member, memberId, 0);
        return ResponseEntity.ok().body(productDetails);
    }
}
