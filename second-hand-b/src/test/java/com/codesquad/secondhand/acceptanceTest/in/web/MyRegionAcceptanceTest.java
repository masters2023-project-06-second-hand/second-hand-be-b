package com.codesquad.secondhand.acceptanceTest.in.web;

import static com.codesquad.secondhand.acceptanceTest.in.web.MemberRegionSteps.멤버의_지역목록_조회를_검증한다;
import static com.codesquad.secondhand.acceptanceTest.in.web.MemberRegionSteps.멤버의_지역목록을_조회한다;
import static com.codesquad.secondhand.acceptanceTest.in.web.MemberRegionSteps.멤버의_지역선택을_검증한다;
import static com.codesquad.secondhand.acceptanceTest.in.web.MemberRegionSteps.멤버의_지역을_삭제한다;
import static com.codesquad.secondhand.acceptanceTest.in.web.MemberRegionSteps.멤버의_지역을_선택한다;
import static com.codesquad.secondhand.acceptanceTest.in.web.MemberRegionSteps.멤버의_지역을_추가한다;
import static com.codesquad.secondhand.utils.RestDocsUtils.출력_필드_추가;
import static org.assertj.core.api.Assertions.assertThat;

import com.codesquad.secondhand.utils.AcceptanceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

class MyRegionAcceptanceTest extends AcceptanceTest {

    public static final Long 이안_아이디 = 1L;

    @Test
    @DisplayName("멤버에 대한 지역 추가 요청을 받으면 요청을 수행하고 201 상태코드로 응답한다.")
    void addRegionToMember() {
        출력_필드_추가("memberRegion_add", spec);

        //given
        Long regionId = 2L;

        //when
        var response = 멤버의_지역을_추가한다(이안_아이디, regionId, ayaanAccessToken, spec);

        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    @Test
    @DisplayName("멤버의 지역 삭제 요청을 받으면 요청을 수행하고 204 상태코드로 응답한다.")
    void removeRegionFromMember() {
        출력_필드_추가("memberRegion_remove", spec);

        //given
        Long 삭제될_리전_아이디 = 1L;
        Long 다른_리전_아이디 = 2L;
        멤버의_지역을_추가한다(이안_아이디, 삭제될_리전_아이디, ayaanAccessToken);
        멤버의_지역을_추가한다(이안_아이디, 다른_리전_아이디, ayaanAccessToken);

        //when
        var response = 멤버의_지역을_삭제한다(이안_아이디, 삭제될_리전_아이디, ayaanAccessToken, spec);

        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @Test
    @DisplayName("멤버의 지역 목록 조회 요청을 받으면 멤버의 지역 목록을 반환한다.")
    void getRegionsOfMember() {
        출력_필드_추가("memberRegion_getRegionsByMember", spec);

        //given
        Long regionId1 = 3L;
        멤버의_지역을_추가한다(이안_아이디, regionId1, ayaanAccessToken);

        //when
        var response = 멤버의_지역목록을_조회한다(이안_아이디, ayaanAccessToken, spec);

        //then
        멤버의_지역목록_조회를_검증한다(response);
    }

    @Test
    @DisplayName("멤버의 지역 선택 요청이 오면 요청을 수행하고 200 상태코드로 응답한다.")
    void selectRegionForMember() {
        출력_필드_추가("memberRegion_select", spec);

        //given
        Long regionId = 2L;
        멤버의_지역을_추가한다(이안_아이디, regionId, ayaanAccessToken);

        //when
        var response = 멤버의_지역을_선택한다(이안_아이디, regionId, ayaanAccessToken, spec);

        //then
        멤버의_지역선택을_검증한다(이안_아이디, ayaanAccessToken, response);
    }
}
