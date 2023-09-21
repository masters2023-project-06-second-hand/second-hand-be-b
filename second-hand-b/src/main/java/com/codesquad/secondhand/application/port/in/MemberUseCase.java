package com.codesquad.secondhand.application.port.in;

import com.codesquad.secondhand.adapter.in.web.response.CategorySimpleDetail;
import com.codesquad.secondhand.adapter.in.web.response.MemberInfo;
import com.codesquad.secondhand.domain.member.Member;
import java.util.List;

public interface MemberUseCase {

    MemberInfo getProfile(Member member, Long memberId);

    List<CategorySimpleDetail> fetchMemberInterestCategories(Member member, long memberId);

    Member getByEmail(String email);
}
