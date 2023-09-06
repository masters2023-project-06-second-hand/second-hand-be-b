package com.codesquad.secondhand.adapter.in.web;

import static org.assertj.core.api.Assertions.assertThat;

import com.codesquad.secondhand.utils.AcceptanceTest;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

class CategoryAcceptanceTest extends AcceptanceTest {

    @Test
    @DisplayName("카테고리 목록 조회 요청을 받으면 카테고리 목록을 반환한다.")
    void getCategories() {
        //when
        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .queryParam("includeImages", false)
                .auth().oauth2(ayaanAccessToken)
                .when().get("/api/categories")
                .then().log().all().extract();

        //then
        Assertions.assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(response.jsonPath().getList("id")).isNotEmpty(),
                () -> assertThat(response.jsonPath().getList("name")).isNotEmpty()
        );
    }

    @Test
    @DisplayName("카테고리 목록 조회 요청을 받으면 includeImages가 true인 경우 imgUrl을 포함하는 카테고리 목록을 반환한다.")
    void getCategoriesWithImgUrl() {
        //when
        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .queryParam("includeImages", true)
                .auth().oauth2(ayaanAccessToken)
                .when().get("/api/categories")
                .then().log().all().extract();

        //then
        Assertions.assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(response.jsonPath().getList("id")).isNotEmpty(),
                () -> assertThat(response.jsonPath().getList("name")).isNotEmpty(),
                () -> assertThat(response.jsonPath().getList("imgUrl")).isNotEmpty()
        );
    }
}
