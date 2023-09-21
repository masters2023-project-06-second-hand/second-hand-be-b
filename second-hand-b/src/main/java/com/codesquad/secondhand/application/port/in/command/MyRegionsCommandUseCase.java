package com.codesquad.secondhand.application.port.in.command;

public interface MyRegionsCommandUseCase {

    /**
     * memberId에 해당하는 멤버에 regionId에 해당하는 지역을 추가한다.
     *
     * @param memberId Member의 식별 ID
     * @param regionId Region의 식별 ID
     */
    void addRegionToMember(Long memberId, Long regionId);

    /**
     * memberId에 해당하는 멤버의 regionId에 해당하는 지역을 삭제한다.
     *
     * @param memberId Member의 식별 ID
     * @param regionId Region의 식별 ID
     */
    void removeRegionFromMember(Long memberId, Long regionId);

    /**
     * memberId에 해당하는 멤버의 selectedRegionId를 regionId로 선택(수정)한다.
     *
     * @param memberId Member의 식별 ID
     * @param regionId 선택되는 Region의 식별 ID
     */
    void selectRegionForMember(Long memberId, Long regionId);
}
