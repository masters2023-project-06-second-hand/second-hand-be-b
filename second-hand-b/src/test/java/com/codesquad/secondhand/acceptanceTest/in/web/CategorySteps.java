package com.codesquad.secondhand.acceptanceTest.in.web;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Assertions;
import org.springframework.http.HttpStatus;

public class CategorySteps {

    public static void 카테고리_목록조회를_검증한다(ExtractableResponse<Response> response) {
        Assertions.assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(response.jsonPath().getList("id")).isNotEmpty(),
                () -> assertThat(response.jsonPath().getList("name")).isNotEmpty()
        );
    }

    public static void 이미지포함_카테고리_목록조회를_검증한다(ExtractableResponse<Response> response) {
        Assertions.assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(response.jsonPath().getList("id")).isNotEmpty(),
                () -> assertThat(response.jsonPath().getList("name")).isNotEmpty(),
                () -> assertThat(response.jsonPath().getList("imgUrl")).isNotEmpty()
        );
    }

    public static ExtractableResponse<Response> 카테고리_목록을_조회한다(boolean includeImages, String accessToken,
            RequestSpecification specification) {
        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .spec(specification)
                .queryParam("includeImages", includeImages)
                .auth().oauth2(accessToken)
                .when().get("/api/categories")
                .then().log().all().extract();
        return response;
    }
}
