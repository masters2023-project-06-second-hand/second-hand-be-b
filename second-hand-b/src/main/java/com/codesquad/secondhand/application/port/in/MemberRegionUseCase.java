package com.codesquad.secondhand.application.port.in;

public interface MemberRegionUseCase {

    /**
     * memberId에 해당하는 멤버에 regionId에 해당하는 지역을 추가한다.
     *
     * @param memberId Member의 식별 ID
     * @param regionId Region의 식별 ID
     */
    void addRegionToMember(Long memberId, Long regionId);
}
