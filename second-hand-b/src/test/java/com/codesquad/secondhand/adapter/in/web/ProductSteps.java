package com.codesquad.secondhand.adapter.in.web;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

public class ProductSteps {

    public static ExtractableResponse<Response> 상품을_등록한다(String accessToken) throws IOException {
        String filePath = "/image/test.jpg";
        File file = new ClassPathResource(filePath).getFile();
        이미지를_업로드한다(file, accessToken);
        filePath = "/image/test2.jpg";
        file = new ClassPathResource(filePath).getFile();
        이미지를_업로드한다(file, accessToken);

        Map<String, Object> body = new HashMap<>();
        body.put("name", "상품명");
        body.put("categoryId", 1);
        body.put("price", 100000);
        body.put("content", "상품 내용");
        body.put("regionId", 1);
        body.put("imagesId", List.of(1, 2));

        return RestAssured.given().contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer " + accessToken)
                .log().all().body(body)
                .when().post("/api/products")
                .then().log().all().extract();
    }

    public static void 상품등록을_검증한다(ExtractableResponse<Response> response) {
        assertThat(response.jsonPath().getString("id")).isEqualTo("1");
    }

    public static ExtractableResponse<Response> 상품상세를_조회한다(Long id, String accessToken) {
        return RestAssured.given().log().all()
                .header("Authorization", "Bearer " + accessToken)
                .when().get("/api/products/{productId}", id)
                .then().log().all().extract();
    }

    public static void 상품상세조회를_검증한다(ExtractableResponse<Response> response) {
        Assertions.assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(response.jsonPath().getString("id")).isEqualTo("1"),
                () -> assertThat(response.jsonPath().getString("productName")).isEqualTo("상품명"),
                () -> assertThat(response.jsonPath().getString("content")).isEqualTo("상품 내용"),
                () -> assertThat(response.jsonPath().getInt("price")).isEqualTo(100000),
                () -> assertThat(response.jsonPath().getString("categoryName")).isEqualTo("인기매물"),
                () -> assertThat(response.jsonPath().getString("regionName")).isEqualTo("서울 강남구 역삼1동"),
                () -> assertThat(response.jsonPath().getList("images.id")).containsExactly(1, 2)
        );
    }

    public static ExtractableResponse<Response> 상품을_수정한다(Long id, String accessToken) {
        Map<String, Object> body = new HashMap<>();
        body.put("name", "상품명 수정");
        body.put("categoryId", 2);
        body.put("price", 200000);
        body.put("content", "상품 내용을 수정");
        body.put("regionId", 3);
        body.put("imagesId", List.of(1));

        return RestAssured.given().log().all()
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .when().put("/api/products/{productId}", id)
                .then().log().all().extract();
    }

    public static void 상품수정을_검증한다(Long id, String accessToken, ExtractableResponse<Response> response) {
        var modifiedProduct = 상품상세를_조회한다(id, accessToken);
        Assertions.assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(modifiedProduct.jsonPath().getString("id")).isEqualTo("1"),
                () -> assertThat(modifiedProduct.jsonPath().getString("productName")).isEqualTo("상품명 수정"),
                () -> assertThat(modifiedProduct.jsonPath().getString("content")).isEqualTo("상품 내용을 수정"),
                () -> assertThat(modifiedProduct.jsonPath().getInt("price")).isEqualTo(200000),
                () -> assertThat(modifiedProduct.jsonPath().getString("categoryName")).isEqualTo("부동산"),
                () -> assertThat(modifiedProduct.jsonPath().getString("regionName")).isEqualTo("서울 강남구 역삼동"),
                () -> assertThat(modifiedProduct.jsonPath().getList("images.id")).containsExactly(1)
        );
    }

    public static ExtractableResponse<Response> 상품상태를_수정한다(Long id, String accessToken) {
        Map<String, String> body = Map.of("status", "판매완료");
        return RestAssured.given().log().all()
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .when().put("/api/products/{productId}/status", id)
                .then().log().all().extract();
    }

    public static void 상품상태수정을_검증한다(Long id, String accessToken, ExtractableResponse<Response> response) {
        var modifiedProduct = 상품상세를_조회한다(id, accessToken);
        Assertions.assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(modifiedProduct.jsonPath().getString("status")).isEqualTo("판매완료")
        );
    }

    public static ExtractableResponse<Response> 이미지를_업로드한다(File file, String accessToken) throws IOException {
        return RestAssured.given().log().all()
                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                .multiPart("file", file)
                .header("Authorization", "Bearer " + accessToken)
                .when().post("/api/images")
                .then().log().all().extract();
    }

    public static ExtractableResponse<Response> 이미지를_삭제한다(Long id, String accessToken) {
        Map<String, Long> body = Map.of("id", id);
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer " + accessToken)
                .body(body)
                .when().delete("/api/images")
                .then().log().all().extract();
    }
}
