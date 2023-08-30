package com.codesquad.secondhand.adapter.in.web;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.Map;
import org.springframework.http.MediaType;

public class MemberRegionSteps {

    public static ExtractableResponse<Response> 멤버의_지역을_추가한다(Long memberId, Long regionId) {
        Map<String, Object> body = Map.of("id", regionId);

        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .when().post("/api/members/{memberId}/regions", memberId)
                .then().log().all().extract();
    }

    public static ExtractableResponse<Response> 멤버의_지역을_삭제한다(Long memberId, Long regionId) {
        Map<String, Object> body = Map.of("id", regionId);

        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .when().delete("/api/members/{memberId}/regions", memberId)
                .then().log().all().extract();
    }
}
