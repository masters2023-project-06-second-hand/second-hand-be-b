package com.codesquad.secondhand.adapter.in.web;

import com.codesquad.secondhand.application.port.in.MemberUseCase;
import com.codesquad.secondhand.application.port.in.request.ToggleProductLikeStatusRequest;
import com.codesquad.secondhand.domain.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RequiredArgsConstructor
@Controller
public class MemberController {

    private final MemberUseCase memberUseCase;

    @PutMapping("/api/products/{productId}/likes")
    public ResponseEntity<Void> toggleProductLikeStatus(@AuthenticationPrincipal Member member,
            @RequestBody ToggleProductLikeStatusRequest isLiked, @PathVariable Long productId) {
        memberUseCase.toggleProductLikeStatus(member, productId, isLiked.isLiked());
        return ResponseEntity.noContent().build();
    }
}
