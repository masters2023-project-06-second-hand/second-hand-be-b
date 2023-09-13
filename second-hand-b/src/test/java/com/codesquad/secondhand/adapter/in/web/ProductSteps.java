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

    public static ExtractableResponse<Response> 상품을_등록한다(String accessToken, int categoryId) {
        String filePath = "/image/test.jpg";
        try {
            File file = new ClassPathResource(filePath).getFile();
            상품용_이미지를_업로드한다(file, accessToken);
            filePath = "/image/test2.jpg";
            file = new ClassPathResource(filePath).getFile();
            상품용_이미지를_업로드한다(file, accessToken);
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        Map<String, Object> body = new HashMap<>();
        body.put("name", "상품명");
        body.put("categoryId", categoryId);
        body.put("price", 100000);
        body.put("content", "상품 내용");
        body.put("regionId", 1);
        body.put("imagesId", List.of(1, 2));

        return RestAssured.given().contentType(MediaType.APPLICATION_JSON_VALUE)
                .auth().oauth2(accessToken)
                .log().all().body(body)
                .when().post("/api/products")
                .then().log().all().extract();
    }

    public static ExtractableResponse<Response> 잘못된_상품_등록요청(String accessToken) {
        Map<String, Object> body = new HashMap<>();
        body.put("name", "상품");
        body.put("categoryId", -1);
        body.put("price", -1);
        body.put("content", "2개");
        body.put("regionId", -1);
        body.put("imagesId", List.of(-1, -2));

        return RestAssured.given().contentType(MediaType.APPLICATION_JSON_VALUE)
                .auth().oauth2(accessToken)
                .log().all().body(body)
                .when().post("/api/products")
                .then().log().all().extract();
    }

    public static void 상품등록을_검증한다(ExtractableResponse<Response> response) {
        assertThat(response.jsonPath().getString("id")).isEqualTo("1");
    }

    public static void 잘못된_상품등록_응답_검증한다(ExtractableResponse<Response> response) {
        Assertions.assertAll(
                () -> assertThat(response.jsonPath().getString("status")).isNotNull(),
                () -> assertThat(response.jsonPath().getString("message.imagesId")).isNotNull(),
                () -> assertThat(response.jsonPath().getString("message.regionId")).isNotNull(),
                () -> assertThat(response.jsonPath().getString("message.price")).isNotNull(),
                () -> assertThat(response.jsonPath().getString("message.name")).isNotNull(),
                () -> assertThat(response.jsonPath().getString("message.content")).isNotNull(),
                () -> assertThat(response.jsonPath().getString("message.categoryId")).isNotNull()
        );
    }

    public static ExtractableResponse<Response> 상품상세를_조회한다(Long id, String accessToken) {
        return RestAssured.given().log().all()
                .auth().oauth2(accessToken)
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
                .auth().oauth2(accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .when().put("/api/products/{productId}", id)
                .then().log().all().extract();
    }

    public static ExtractableResponse<Response> 잘못된_요청으로_상품을_수정한다(String accessToken) {
        Map<String, Object> body = new HashMap<>();
        body.put("name", "상품");
        body.put("categoryId", -1);
        body.put("price", -1);
        body.put("content", "상품");
        body.put("regionId", -1);
        body.put("imagesId", List.of(-1, -1));

        return RestAssured.given().log().all()
                .auth().oauth2(accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .when().put("/api/products/{productId}", -1)
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

    public static void 잘못된_상품수정_요청을_검증한다(ExtractableResponse<Response> response) {
        Assertions.assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
                () -> assertThat(response.jsonPath().getString("message.imagesId")).isNotNull(),
                () -> assertThat(response.jsonPath().getString("message.regionId")).isNotNull(),
                () -> assertThat(response.jsonPath().getString("message.price")).isNotNull(),
                () -> assertThat(response.jsonPath().getString("message.name")).isNotNull(),
                () -> assertThat(response.jsonPath().getString("message.content")).isNotNull(),
                () -> assertThat(response.jsonPath().getString("message.categoryId")).isNotNull()
        );
    }

    public static ExtractableResponse<Response> 상품상태를_수정한다(Long id, String accessToken) {
        Map<String, String> body = Map.of("status", "판매완료");
        return RestAssured.given().log().all()
                .auth().oauth2(accessToken)
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

    public static ExtractableResponse<Response> 상품용_이미지를_업로드한다(File file, String accessToken) throws IOException {
        return RestAssured.given().log().all()
                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                .multiPart("file", file)
                .auth().oauth2(accessToken)
                .when().post("/api/products/images")
                .then().log().all().extract();
    }

    public static ExtractableResponse<Response> 회원용_이미지를_업로드한다(File file, String accessToken) throws IOException {
        return RestAssured.given().log().all()
                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                .multiPart("file", file)
                .auth().oauth2(accessToken)
                .when().post("/api/members/images")
                .then().log().all().extract();
    }

    public static ExtractableResponse<Response> 이미지를_삭제한다(Long id, String accessToken) {
        Map<String, Long> body = Map.of("id", id);
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .auth().oauth2(accessToken)
                .body(body)
                .when().delete("/api/images")
                .then().log().all().extract();
    }

    public static ExtractableResponse<Response> regionId로_상품목록을_조회한다(Long regionId, String accessToken) {
        return RestAssured.given().log().all()
                .queryParam("regionId", regionId)
                .auth().oauth2(accessToken)
                .when().get("/api/products")
                .then().log().all().extract();
    }

    public static ExtractableResponse<Response> regionId와_categoryId로_지역목록을_조회한다(Long regionId, Long categoryId,
            String accessToken) {
        return RestAssured.given().log().all()
                .queryParam("regionId", regionId)
                .queryParam("categoryId", categoryId)
                .auth().oauth2(accessToken)
                .when().get("/api/products")
                .then().log().all().extract();
    }

    public static ExtractableResponse<Response> 상품을_삭제한다(long productId, String accessToken) {
        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .auth().oauth2(accessToken)
                .when().delete("/api/products/{productId}", productId)
                .then().log().all().extract();
        return response;
    }
}
