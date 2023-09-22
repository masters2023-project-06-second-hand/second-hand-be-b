package com.codesquad.secondhand.acceptanceTest.in.web;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

public class MemberRegionSteps {

    public static ExtractableResponse<Response> 멤버의_지역을_추가한다(Long memberId, Long regionId, String accessToken) {
        return 멤버의_지역을_추가한다(memberId, regionId, accessToken, new RequestSpecBuilder().build());
    }

    public static ExtractableResponse<Response> 멤버의_지역을_추가한다(Long memberId, Long regionId, String accessToken,
            RequestSpecification specification) {
        Map<String, Object> body = Map.of("id", regionId);

        return RestAssured.given().log().all()
                .spec(specification)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .auth().oauth2(accessToken)
                .body(body)
                .when().post("/api/members/{memberId}/regions", memberId)
                .then().log().all().extract();
    }

    public static ExtractableResponse<Response> 멤버의_지역을_삭제한다(Long memberId, Long regionId, String accessToken,
            RequestSpecification specification) {
        Map<String, Object> body = Map.of("id", regionId);

        return RestAssured.given().log().all()
                .spec(specification)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .auth().oauth2(accessToken)
                .body(body)
                .when().delete("/api/members/{memberId}/regions", memberId)
                .then().log().all().extract();
    }

    public static ExtractableResponse<Response> 멤버의_지역목록을_조회한다(Long memberId, String accessToken) {
        return 멤버의_지역목록을_조회한다(memberId, accessToken, new RequestSpecBuilder().build());
    }

    public static ExtractableResponse<Response> 멤버의_지역목록을_조회한다(Long memberId, String accessToken,
            RequestSpecification specification) {
        return RestAssured.given().log().all()
                .spec(specification)
                .auth().oauth2(accessToken)
                .when().get("/api/members/{memberId}/regions", memberId)
                .then().log().all().extract();
    }

    public static void 멤버의_지역목록_조회를_검증한다(ExtractableResponse<Response> response) {
        Assertions.assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(response.jsonPath().getLong("selectedRegionId")).isEqualTo(1L),
                () -> assertThat(response.jsonPath().getList("regions")).hasSize(2),
                () -> assertThat(response.jsonPath().getList("regions.id")).contains(1, 3)
        );
    }

    public static ExtractableResponse<Response> 멤버의_지역을_선택한다(Long memberId, Long regionId, String accessToken,
            RequestSpecification specification) {
        Map<String, Object> body = Map.of("id", regionId);
        return RestAssured.given().log().all()
                .spec(specification)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .auth().oauth2(accessToken)
                .body(body)
                .when().put("/api/members/{memberId}/regions", memberId)
                .then().log().all().extract();
    }

    public static void 멤버의_지역선택을_검증한다(Long memberId, String accessToken, ExtractableResponse<Response> response) {
        var memberRegionList = 멤버의_지역목록을_조회한다(memberId, accessToken);
        Assertions.assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(memberRegionList.jsonPath().getLong("selectedRegionId")).isEqualTo(2)
        );
    }
}
