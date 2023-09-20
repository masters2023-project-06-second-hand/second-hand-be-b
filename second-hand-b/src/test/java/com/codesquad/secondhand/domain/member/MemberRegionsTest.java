package com.codesquad.secondhand.domain.member;

import static org.assertj.core.api.Assertions.assertThat;

import com.codesquad.secondhand.domain.region.Region;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MemberRegionsTest {

    @Test
    @DisplayName("멤버의 지역목록에 지역을 추가한다")
    void addRegion() {
        // given
        MemberRegions memberRegions = new MemberRegions();
        Region region = new Region("강남구 역삼1동");

        // when
        memberRegions.addRegion(region);

        // then
        List<Region> regions = memberRegions.getRegions();
        assertThat(regions.get(0).getName()).isEqualTo(region.getName());
    }

    @Test
    @DisplayName("멤버의 지역목록에서 지역을 삭제한다")
    void removeRegion() {
        // given
        MemberRegions memberRegions = new MemberRegions();
        Region region1 = new Region("강남구 역삼1동");
        memberRegions.addRegion(region1);
        Region region2 = new Region("강남구 역삼2동");
        memberRegions.addRegion(region2);


        // when
        memberRegions.removeRegion(region1);

        // then
        List<Region> regions = memberRegions.getRegions();
        assertThat(regions).size().isEqualTo(1);
    }
}
