package com.codesquad.secondhand.command.domain.member;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MyRegionsTest {

    @Test
    @DisplayName("멤버의 지역목록에 지역을 추가한다")
    void addRegion() {
        // given
        MyRegions myRegions = new MyRegions();

        // when
        myRegions.addRegion(6);

        // then
        List<Long> regions = myRegions.getRegions();
        assertThat(regions.get(0)).isEqualTo(6);
    }

    @Test
    @DisplayName("멤버의 지역목록에서 지역을 삭제한다")
    void removeRegion() {
        // given
        MyRegions myRegions = new MyRegions();
        myRegions.addRegion(1);
        myRegions.addRegion(2);

        // when
        myRegions.removeRegion(1);

        // then
        List<Long> regions = myRegions.getRegions();
        assertThat(regions).size().isEqualTo(1);
    }
}
