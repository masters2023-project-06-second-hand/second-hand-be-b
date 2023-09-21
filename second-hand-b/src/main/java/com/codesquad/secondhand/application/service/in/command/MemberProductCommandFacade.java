package com.codesquad.secondhand.application.service.in.command;

import com.codesquad.secondhand.application.port.in.command.MemberProductCommandUseCase;
import com.codesquad.secondhand.application.service.in.MemberService;
import com.codesquad.secondhand.application.service.in.prodcut.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
public class MemberProductCommandFacade implements MemberProductCommandUseCase {

    private final ProductService productService;
    private final MemberService memberService;

    @Transactional
    @Override
    public void toggleProductLikeStatus(String validatedMemberId, Long productId, boolean isLiked) {
        productService.validateProductId(productId);
        memberService.toggleProductLikeStatus(validatedMemberId, isLiked, productId);
    }
}
