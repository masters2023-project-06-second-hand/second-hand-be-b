package com.codesquad.secondhand.application.port.in.query;

import com.codesquad.secondhand.adapter.in.web.query.member.response.MemberInfo;
import com.codesquad.secondhand.adapter.in.web.query.prodcut.response.CategorySimpleDetail;
import java.util.List;

public interface MemberQueryUseCase {

    MemberInfo getProfile(String validatedMemberId, Long memberId);

    List<CategorySimpleDetail> fetchMemberInterestCategories(String validatedMemberId, long memberId);
}
