package com.codesquad.secondhand.adapter.in.web;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

public class AcceptanceSteps {

    public static void 상품등록을_검증한다(ExtractableResponse<Response> response) {
        assertThat(response.jsonPath().getString("id")).isEqualTo("1");
    }

    public static ExtractableResponse<Response> 상품을_등록한다() {
        Map<String, Object> body = new HashMap<>();
        body.put("name", "상품명");
        body.put("categoryId", "1");
        body.put("price", "100000");
        body.put("content", "상품 내용");
        body.put("region", "2");
        body.put("imagesId", List.of("1", "2"));

        return RestAssured.given().contentType(MediaType.APPLICATION_JSON_VALUE)
                .log().all().body(body)
                .when().post("/api/products")
                .then().log().all().extract();
    }

    public static void 상품_상세를_검증한다(ExtractableResponse<Response> response) {
        Assertions.assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(response.jsonPath().getString("id")).isEqualTo("1"),
                () -> assertThat(response.jsonPath().getString("productName")).isEqualTo("상품명"),
                () -> assertThat(response.jsonPath().getString("content")).isEqualTo("상품 내용"),
                () -> assertThat(response.jsonPath().getInt("price")).isEqualTo(100000)
        );
    }

    public static ExtractableResponse<Response> 상품_상세를_조회한다(Long id) {
        return RestAssured.given().log().all()
                .when().get("/api/products/{productId}", id)
                .then().log().all().extract();
    }
}
