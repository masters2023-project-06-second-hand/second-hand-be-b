package com.codesquad.secondhand.query.service;

import com.codesquad.secondhand.query.controller.member.response.MemberInfo;
import com.codesquad.secondhand.query.controller.prodcut.response.CategorySimpleDetail;
import com.codesquad.secondhand.query.port.MemberQueryUseCase;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
public class MemberQueryFacade implements MemberQueryUseCase {

    private final CategoryQueryService categoryService;
    private final MemberQueryService memberQueryService;


    @Transactional(readOnly = true)
    @Override
    public MemberInfo getProfile(String validatedMemberId, Long memberId) {
        return memberQueryService.getProfile(validatedMemberId, memberId);
    }

    @Transactional(readOnly = true)
    @Override
    public List<CategorySimpleDetail> fetchMemberInterestCategories(String validatedMemberId, long memberId) {
        return categoryService.getLikesCategoryByMemberId(validatedMemberId,memberId);
    }
}
