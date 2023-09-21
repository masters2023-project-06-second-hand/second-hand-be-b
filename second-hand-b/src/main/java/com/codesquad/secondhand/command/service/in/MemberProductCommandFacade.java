package com.codesquad.secondhand.command.service.in;

import com.codesquad.secondhand.command.port.in.MemberProductCommandUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
public class MemberProductCommandFacade implements MemberProductCommandUseCase {

    private final ProductCommandService productCommandService;
    private final MemberCommandService memberCommandService;

    @Transactional
    @Override
    public void toggleProductLikeStatus(String validatedMemberId, Long productId, boolean isLiked) {
        productCommandService.validateProductId(productId);
        memberCommandService.toggleProductLikeStatus(validatedMemberId, isLiked, productId);
    }
}
