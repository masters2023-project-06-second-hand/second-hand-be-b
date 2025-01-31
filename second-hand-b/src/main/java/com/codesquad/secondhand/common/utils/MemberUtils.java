package com.codesquad.secondhand.common.utils;

import com.codesquad.secondhand.common.exception.PermissionDeniedException;

public class MemberUtils {

    private MemberUtils() {
        throw new UnsupportedOperationException();
    }

    public static void validateMemberPermission(String validatedMemberId, long memberId) {
        if (Long.parseLong(validatedMemberId) != memberId) {
            throw new PermissionDeniedException();
        }
    }

}
