package com.codesquad.secondhand.adapter.in.web;

import static org.assertj.core.api.Assertions.assertThat;

import com.codesquad.secondhand.utils.AcceptanceTest;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RegionAcceptanceTest extends AcceptanceTest {

    @Test
    @DisplayName("지역 조회 요청을 받으면 page에 해당하는 지역 목록을 size 만큼 조회하여 반환한다.")
    void searchRegionsByName() {
        //given
        int page = 0;
        int size = 10;
        String word = "";

        //when
        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .queryParam("page", page)
                .queryParam("size", size)
                .queryParam("word", word)
                .auth().oauth2(ayaanAccessToken)
                .when().get("/api/regions")
                .then().log().all().extract();

        //then
        Assertions.assertAll(
                () -> assertThat(response.jsonPath().getBoolean("hasNext")).isTrue(),
                () -> assertThat(response.jsonPath().getInt("page")).isEqualTo(page),
                () -> assertThat(response.jsonPath().getList("regions")).hasSize(size)
        );
    }
}
