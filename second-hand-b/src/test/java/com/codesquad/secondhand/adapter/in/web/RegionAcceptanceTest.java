package com.codesquad.secondhand.adapter.in.web;

import static com.codesquad.secondhand.adapter.in.web.RegionSteps.지역명_지역목록_조회를_검증한다;
import static com.codesquad.secondhand.adapter.in.web.RegionSteps.지역목록_조회를_검증한다;
import static com.codesquad.secondhand.adapter.in.web.RegionSteps.지역목록을_조회한다;
import static com.codesquad.secondhand.utils.RestDocsUtils.출력_필드_추가;

import com.codesquad.secondhand.utils.AcceptanceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RegionAcceptanceTest extends AcceptanceTest {

    @Test
    @DisplayName("page에 해당하는 지역 목록을 size 만큼 조회하여 반환한다.")
    void searchRegions() {
        출력_필드_추가("region_getRegions", spec);

        //given
        int page = 0;
        int size = 10;

        //when
        var response = 지역목록을_조회한다(page, size, ayaanAccessToken, spec);

        //then
        지역목록_조회를_검증한다(page, size, response);
    }

    @Test
    @DisplayName("지역명에 해당하는 지역목록을 조회하여 반환한다.")
    void searchRegionsByName() {
        출력_필드_추가("region_getRegionsByName", spec);

        //given
        int page = 0;
        int size = 10;
        String word = "역삼";

        //when
        var response = 지역목록을_조회한다(page, size, word, ayaanAccessToken, spec);

        //then
        지역명_지역목록_조회를_검증한다(page, response);
    }
}
