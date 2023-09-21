package com.codesquad.secondhand.application.service.in.command;

import com.codesquad.secondhand.application.port.in.command.MemberProductCommandUseCase;
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
