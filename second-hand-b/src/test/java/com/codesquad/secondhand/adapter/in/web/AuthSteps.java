package com.codesquad.secondhand.adapter.in.web;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.springframework.http.MediaType;

public class AuthSteps {

    public static void 토큰_검증(ExtractableResponse<Response> 회원_토큰) {
        Assertions.assertAll(
                () -> assertThat(회원_토큰.jsonPath().getString("accessToken")).isNotNull(),
                () -> assertThat(회원_토큰.jsonPath().getString("refreshToken")).isNotNull()
        );
    }

    public static ExtractableResponse<Response> 회원_가입_한다(String signUpToken,
            RequestSpecification specification) {
        Map<String, Object> body = Map.of(
                "nickname", "이안",
                "profileImg", "url",
                "regionsId", List.of(1)
        );
        return RestAssured.given().log().all()
                .spec(specification)
                .body(body).contentType(MediaType.APPLICATION_JSON_VALUE)
                .auth().oauth2(signUpToken)
                .when().post("/api/members/signup")
                .then().log().all()
                .extract();
    }
}
