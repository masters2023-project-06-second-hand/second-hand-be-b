package com.codesquad.secondhand.acceptanceTest.in.web;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.assertj.core.api.AssertionsForInterfaceTypes;
import org.junit.jupiter.api.Assertions;

public class RegionSteps {

    public static ExtractableResponse<Response> 지역목록을_조회한다(int page, int size, String accessToken,
            RequestSpecification specification) {
        return RestAssured.given().log().all()
                .spec(specification)
                .queryParam("page", page)
                .queryParam("size", size)
                .auth().oauth2(accessToken)
                .when().get("/api/regions")
                .then().log().all().extract();
    }

    public static void 지역목록_조회를_검증한다(int page, int size, ExtractableResponse<Response> response) {
        Assertions.assertAll(
                () -> assertThat(response.jsonPath().getBoolean("hasNext")).isTrue(),
                () -> assertThat(response.jsonPath().getInt("page")).isEqualTo(page),
                () -> AssertionsForInterfaceTypes.assertThat(response.jsonPath().getList("regions")).hasSize(size)
        );
    }

    public static ExtractableResponse<Response> 지역목록을_조회한다(int page, int size, String word, String accessToken,
            RequestSpecification specification) {
        return RestAssured.given().log().all()
                .spec(specification)
                .queryParam("page", page)
                .queryParam("size", size)
                .queryParam("word", word)
                .auth().oauth2(accessToken)
                .when().get("/api/regions")
                .then().log().all().extract();
    }

    public static void 지역명_지역목록_조회를_검증한다(int page, ExtractableResponse<Response> response) {
        Assertions.assertAll(
                () -> assertThat(response.jsonPath().getBoolean("hasNext")).isFalse(),
                () -> assertThat(response.jsonPath().getInt("page")).isEqualTo(page),
                () -> AssertionsForInterfaceTypes.assertThat(response.jsonPath().getList("regions")).hasSize(3)
        );
    }
}
