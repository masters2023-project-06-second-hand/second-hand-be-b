package com.codesquad.secondhand.adapter.in.web;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

public class MemberSteps {

    public static void 관심상품은_담은_응답_검증(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    public static ExtractableResponse<Response> 관심상품에_담는다(String id, String accessToken) {
        Map<String, Boolean> body = new HashMap<>();
        body.put("isLiked", true);
        return RestAssured.given().log().all()
                .auth().oauth2(accessToken)
                .body(body).contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().put("/api/products/{productId}/likes", id)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 관심상품에_제거한다(String id, String accessToken) {
        Map<String, Boolean> body = new HashMap<>();
        body.put("liked", false);
        return RestAssured.given().log().all()
                .auth().oauth2(accessToken)
                .body(body).contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().put("/api/products/{productId}/likes", id)
                .then().log().all()
                .extract();
    }

    public static void 나의_관심상품_목록_조회_검증한다(ExtractableResponse<Response> response) {
        assertThat(response.jsonPath().getList("id", String.class))
                .containsExactlyInAnyOrder("1", "2");
    }

    public static void 나의_카테고리별_관심상품_목록_조회_검증한다(ExtractableResponse<Response> response) {
        assertThat(response.jsonPath().getList("id", String.class))
                .containsExactlyInAnyOrder("1");
    }

    public static ExtractableResponse<Response> 나의_광심상품_목록_조회한다(String accessToken) {
        return RestAssured.given().log().all()
                .auth().oauth2(accessToken)
                .when().get("/api/members/{memberId}/likes", 2)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 나의_광심상품_목록_조회한다(String accessToken, int categoryId) {
        return RestAssured.given().log().all()
                .auth().oauth2(accessToken).param("categoryId", categoryId)
                .when().get("/api/members/{memberId}/likes", 2)
                .then().log().all()
                .extract();
    }

}
