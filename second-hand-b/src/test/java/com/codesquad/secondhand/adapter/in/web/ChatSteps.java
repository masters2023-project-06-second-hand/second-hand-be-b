package com.codesquad.secondhand.adapter.in.web;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import java.util.Map;
import org.springframework.http.MediaType;

public class ChatSteps {

    public static ExtractableResponse<Response> 채팅방ID를_조회한다(Long productId, Long sellerId, String accessToken,
            RequestSpecification specification) {
        Map<String, Long> body = Map.of("productId", productId, "sellerId", sellerId);
        return RestAssured.given().log().all()
                .spec(specification)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .auth().oauth2(accessToken)
                .when().get("/api/chats/room-id")
                .then().log().all().extract();
    }
}
