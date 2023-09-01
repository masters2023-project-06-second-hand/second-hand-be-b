package com.codesquad.secondhand.adapter.in.web;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

public class MemberRegionSteps {

    public static ExtractableResponse<Response> 멤버의_지역을_추가한다(Long memberId, Long regionId, String accessToken) {
        Map<String, Object> body = Map.of("id", regionId);

        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer " + accessToken)
                .body(body)
                .when().post("/api/members/{memberId}/regions", memberId)
                .then().log().all().extract();
    }

    public static ExtractableResponse<Response> 멤버의_지역을_삭제한다(Long memberId, Long regionId, String accessToken) {
        Map<String, Object> body = Map.of("id", regionId);

        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer " + accessToken)
                .body(body)
                .when().delete("/api/members/{memberId}/regions", memberId)
                .then().log().all().extract();
    }

    public static ExtractableResponse<Response> 멤버의_지역목록을_조회한다(Long memberId, String accessToken) {
        return RestAssured.given().log().all()
                .header("Authorization", "Bearer " + accessToken)
                .when().get("/api/members/{memberId}/regions", memberId)
                .then().log().all().extract();
    }

    public static void 멤버의_지역목록_조회를_검증한다(ExtractableResponse<Response> response) {
        Assertions.assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(response.jsonPath().getLong("selectedRegionId")).isEqualTo(3L),
                () -> assertThat(response.jsonPath().getList("regions")).hasSize(3),
                () -> assertThat(response.jsonPath().getList("regions.id")).contains(1, 2)
        );
    }
}
