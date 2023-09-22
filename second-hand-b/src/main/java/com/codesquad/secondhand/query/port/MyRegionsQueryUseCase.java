package com.codesquad.secondhand.query.port;

import com.codesquad.secondhand.query.controller.member.response.MemberRegionInfos;

public interface MyRegionsQueryUseCase {

    /**
     * memberId에 해당하는 멤버의 선택된 Region ID와 Region 목록을 조회한다.
     *
     * @param memberId Member의 식별 ID
     * @return 선택된 Region ID와 Region 목록을 가지는 DTO
     */
    MemberRegionInfos getRegionsOfMember(Long memberId);
}
