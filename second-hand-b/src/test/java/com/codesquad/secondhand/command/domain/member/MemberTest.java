package com.codesquad.secondhand.command.domain.member;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MemberTest {

    @Test
    @DisplayName("멤버의 지역을 선택한 지역으로 변경한다")
    void selectRegion() {
        // given
        Member member = new Member("test@email.com", "test", "testUrl", Role.MEMBER);
        member.addRegion(1);
        member.addRegion(2);

        // when
        member.selectRegion(2);

        // then
        assertThat(member.getSelectedRegionId()).isEqualTo(2);
    }
}
