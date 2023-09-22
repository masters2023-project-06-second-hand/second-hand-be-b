package com.codesquad.secondhand.command.adapter.in.web.product;

import com.codesquad.secondhand.command.adapter.in.web.product.request.ToggleProductLikeStatusRequest;
import com.codesquad.secondhand.command.port.in.MemberProductCommandUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RequiredArgsConstructor
@Controller
public class MemberProductCommandController {

    private final MemberProductCommandUseCase memberProductCommandUseCase;

    @PutMapping("/api/products/{productId}/likes")
    public ResponseEntity<Void> toggleProductLikeStatus(
            @AuthenticationPrincipal String validatedMemberId,
            @RequestBody ToggleProductLikeStatusRequest toggleProductLikeStatusRequest,
            @PathVariable Long productId
    ) {
        memberProductCommandUseCase.toggleProductLikeStatus(validatedMemberId, productId,
                toggleProductLikeStatusRequest.isLiked());
        return ResponseEntity.noContent().build();
    }

}
