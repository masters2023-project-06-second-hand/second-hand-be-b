package com.codesquad.secondhand.adapter.in.web;

import static com.codesquad.secondhand.adapter.in.web.MemberRegionSteps.멤버의_지역목록_조회를_검증한다;
import static com.codesquad.secondhand.adapter.in.web.MemberRegionSteps.멤버의_지역목록을_조회한다;
import static com.codesquad.secondhand.adapter.in.web.MemberRegionSteps.멤버의_지역선택을_검증한다;
import static com.codesquad.secondhand.adapter.in.web.MemberRegionSteps.멤버의_지역을_삭제한다;
import static com.codesquad.secondhand.adapter.in.web.MemberRegionSteps.멤버의_지역을_선택한다;
import static com.codesquad.secondhand.adapter.in.web.MemberRegionSteps.멤버의_지역을_추가한다;
import static org.assertj.core.api.Assertions.assertThat;

import com.codesquad.secondhand.utils.AcceptanceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

class MemberRegionAcceptanceTest extends AcceptanceTest {
    
    @Test
    @DisplayName("멤버에 대한 지역 추가 요청을 받으면 요청을 수행하고 201 상태코드로 응답한다.")
    void addRegionToMember() {
        //given
        Long memberId = 1L;
        Long regionId = 1L;

        //when
        var response = 멤버의_지역을_추가한다(memberId, regionId, accessToken);

        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    @Test
    @DisplayName("멤버의 지역 삭제 요청을 받으면 요청을 수행하고 204 상태코드로 응답한다.")
    void removeRegionFromMember() {
        //given
        Long memberId = 1L;
        Long regionId = 1L;
        멤버의_지역을_추가한다(memberId, regionId, accessToken);

        //when
        var response = 멤버의_지역을_삭제한다(memberId, regionId, accessToken);

        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @Test
    @DisplayName("멤버의 지역 목록 조회 요청을 받으면 멤버의 지역 목록을 반환한다.")
    void getRegionsOfMember() {
        //given
        Long memberId = 1L;
        Long regionId1 = 1L;
        멤버의_지역을_추가한다(memberId, regionId1, accessToken);

        //when
        var response = 멤버의_지역목록을_조회한다(memberId, accessToken);

        //then
        멤버의_지역목록_조회를_검증한다(response);
    }

    @Test
    @DisplayName("멤버의 지역 선택 요청이 오면 요청을 수행하고 200 상태코드로 응답한다.")
    void selectRegionForMember() {
        //given
        Long memberId = 1L;
        Long regionId = 2L;
        멤버의_지역을_추가한다(memberId, regionId, accessToken);

        //when
        var response = 멤버의_지역을_선택한다(memberId, regionId, accessToken);

        //then
        멤버의_지역선택을_검증한다(memberId, accessToken, response);
    }
}
