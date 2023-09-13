package com.codesquad.secondhand.application.service.in;

import static org.mockito.BDDMockito.given;

import com.codesquad.secondhand.application.service.in.exception.ExistsMemberRegionException;
import com.codesquad.secondhand.application.service.in.exception.NotExistsMemberRegionException;
import com.codesquad.secondhand.domain.member.Member;
import com.codesquad.secondhand.domain.region.Region;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MemberRegionServiceTest {

    @InjectMocks
    MemberRegionService memberRegionService;
    @Mock
    MemberService memberService;
    @Mock
    RegionService regionService;

    @Test
    @DisplayName("멤버의 지역목록에 존재하는 지역을 추가하면 예외를 던진다")
    void addRegionToMember() {
        // given
        Long memberId = 1L;
        Long regionId = 1L;
        Region region = new Region("역삼1동");
        Member member = new Member("test@test", "name", "img", region, null);
        given(memberService.getById(memberId)).willReturn(member);
        given(regionService.getById(regionId)).willReturn(region);

        // when then
        Assertions.assertThatThrownBy(() -> memberRegionService.addRegionToMember(memberId, regionId))
                .isInstanceOf(ExistsMemberRegionException.class);
    }

    @Test
    @DisplayName("멤버의 지역목록에 존재하지 않는 지역을 삭제하면 예외를 던진다")
    void removeRegionFromMember() {
        // given
        Long memberId = 1L;
        Region region = new Region("역삼1동");
        Member member = new Member("test@test", "name", "img", region, null);
        given(memberService.getById(memberId)).willReturn(member);

        Long removeRegionId = 2L;
        Region removeRegion = new Region("역삼2동");
        given(regionService.getById(removeRegionId)).willReturn(removeRegion);

        // when then
        Assertions.assertThatThrownBy(() -> memberRegionService.removeRegionFromMember(memberId, removeRegionId))
                .isInstanceOf(NotExistsMemberRegionException.class);
    }

    @Test
    @DisplayName("멤버의 지역목록에 존재하지 않는 지역을 선택하면 예외를 던진다")
    void selectRegionForMember() {
        // given
        Long memberId = 1L;
        Region region = new Region("역삼1동");
        Member member = new Member("test@test", "name", "img", region, null);
        given(memberService.getById(memberId)).willReturn(member);

        Long selectRegionId = 2L;
        Region selectRegion = new Region("역삼2동");
        given(regionService.getById(selectRegionId)).willReturn(selectRegion);

        // when then
        Assertions.assertThatThrownBy(() -> memberRegionService.selectRegionForMember(memberId, selectRegionId))
                .isInstanceOf(NotExistsMemberRegionException.class);
    }
}
