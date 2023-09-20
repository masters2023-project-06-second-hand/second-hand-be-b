package com.codesquad.secondhand.application.service.in;

import com.codesquad.secondhand.application.service.in.exception.PermissionDeniedException;
import com.codesquad.secondhand.domain.member.Member;

public class MemberUtils {

    private MemberUtils() {
        throw new UnsupportedOperationException();
    }

    public static void validateMemberPermission(Member member, long memberId) {
        if (!member.isSameId(memberId)) {
            throw new PermissionDeniedException();
        }
    }

}
