package com.codesquad.secondhand.query.port;

import com.codesquad.secondhand.query.controller.member.response.MemberInfo;
import com.codesquad.secondhand.query.controller.prodcut.response.CategorySimpleDetail;
import java.util.List;

public interface MemberQueryUseCase {

    MemberInfo getProfile(String validatedMemberId, Long memberId);

    List<CategorySimpleDetail> fetchMemberInterestCategories(String validatedMemberId, long memberId);
}
