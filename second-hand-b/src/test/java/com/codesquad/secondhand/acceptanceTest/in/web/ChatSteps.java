package com.codesquad.secondhand.acceptanceTest.in.web;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import java.util.Map;
import org.springframework.http.MediaType;

public class ChatSteps {

    public static ExtractableResponse<Response> 채팅방ID를_조회한다(Long productId, Long sellerId, String accessToken) {
        return 채팅방ID를_조회한다(productId, sellerId, accessToken, new RequestSpecBuilder().build());
    }

    public static ExtractableResponse<Response> 채팅방ID를_조회한다(Long productId, Long sellerId, String accessToken,
            RequestSpecification specification) {
        Map<String, Long> body = Map.of("productId", productId, "sellerId", sellerId);
        return RestAssured.given().log().all()
                .spec(specification)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .auth().oauth2(accessToken)
                .when().post("/api/chats/room-id")
                .then().log().all().extract();
    }

    public static ExtractableResponse<Response> 채팅방_정보를_조회한다(Long chatRoomId, String accessToken,
            RequestSpecification specification) {
        return RestAssured.given().log().all()
                .spec(specification)
                .auth().oauth2(accessToken)
                .when().get("/api/chats/{chatRoomId}", chatRoomId)
                .then().log().all().extract();
    }

    public static ExtractableResponse<Response> 채팅방_목록을_조회한다(Long memberId, String accessToken,
            RequestSpecification specification) {
        return RestAssured.given().log().all()
                .spec(specification)
                .auth().oauth2(accessToken)
                .when().get("/api/members/{memberId}/chats", memberId)
                .then().log().all().extract();
    }
}
