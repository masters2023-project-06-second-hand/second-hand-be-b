package com.codesquad.secondhand.command.port.in;

public interface MemberProductCommandUseCase {

    void toggleProductLikeStatus(String validatedMemberId, Long productId, boolean liked);
}
