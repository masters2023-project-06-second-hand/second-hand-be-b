package com.codesquad.secondhand.adapter.in.web;

import com.codesquad.secondhand.utils.AcceptanceTest;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RegionAcceptanceTest extends AcceptanceTest {

    @Test
    @DisplayName("지역 조회 요청을 받으면 page에 해당하는 지역을 offset 만큼 word로 필터링된 목록을 반환한다.")
    void searchRegionsByName() {
        //given
        int page = 0;
        int offset = 20;
        String word = "강남";

        //when
        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .queryParam("page", page)
                .queryParam("offset", offset)
                .queryParam("word", word)
                .auth().oauth2(accessToken)
                .when().get("/api/regions")
                .then().log().all().extract();

        //then
        Assertions.assertThat(response.jsonPath().getList(".")).hasSize(offset);
    }

}
