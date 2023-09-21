package com.codesquad.secondhand.application.service.in;

import static com.codesquad.secondhand.application.service.in.MemberUtils.validateMemberPermission;

import com.codesquad.secondhand.adapter.in.web.response.CategorySimpleDetail;
import com.codesquad.secondhand.adapter.in.web.response.MemberInfo;
import com.codesquad.secondhand.application.port.in.MemberUseCase;
import com.codesquad.secondhand.domain.member.Member;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
public class MemberFacade implements MemberUseCase {

    private final CategoryService categoryService;
    private final MemberService memberService;


    @Transactional(readOnly = true)
    @Override
    public MemberInfo getProfile(Member member, Long memberId) {
        return memberService.getProfile(member, memberId);
    }

    @Transactional(readOnly = true)
    @Override
    public List<CategorySimpleDetail> fetchMemberInterestCategories(Member member, long memberId) {
        validateMemberPermission(member, memberId);
        return categoryService.getLikesCategoryByMemberId(memberId);
    }

    @Override
    public Member getByEmail(String email) {
        return memberService.getByEmail(email);
    }
}
