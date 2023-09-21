package com.codesquad.secondhand.application.port.in.command;

public interface MemberProductCommandUseCase {

    void toggleProductLikeStatus(String validatedMemberId, Long productId, boolean liked);
}
