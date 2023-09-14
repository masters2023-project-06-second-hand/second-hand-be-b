package com.codesquad.secondhand.domain.member;

import static org.assertj.core.api.Assertions.assertThat;

import com.codesquad.secondhand.domain.region.Region;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MemberTest {

    @Test
    @DisplayName("멤버의 지역을 선택한 지역으로 변경한다")
    void selectRegion() {
        // given
        Region region = new Region("강남구 역삼1동");
        Member member = new Member("test@email.com", "test", "testUrl", region, Role.MEMBER);
        Region addRegion = new Region("강남구 역삼2동");
        member.addRegion(addRegion);

        // when
        member.selectRegion(addRegion);

        // then
        assertThat(member.getSelectedRegion().getName()).isEqualTo(addRegion.getName());
    }
}
