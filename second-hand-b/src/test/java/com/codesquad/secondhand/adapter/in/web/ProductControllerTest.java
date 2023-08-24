package com.codesquad.secondhand.adapter.in.web;

import static com.codesquad.secondhand.adapter.in.web.AcceptanceSteps.상품_상세를_검증한다;
import static com.codesquad.secondhand.adapter.in.web.AcceptanceSteps.상품_상세를_조회한다;
import static com.codesquad.secondhand.adapter.in.web.AcceptanceSteps.상품등록을_검증한다;
import static com.codesquad.secondhand.adapter.in.web.AcceptanceSteps.상품을_등록한다;
import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.RestAssured;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
class ProductControllerTest {

    @Test
    @DisplayName("상품 등록 요청이 오면 상품 아이디를 반환한다.")
    void create() {
        var response = 상품을_등록한다();

        상품등록을_검증한다(response);
    }

    @Test
    @DisplayName("상품 상세 조회 요청이 오면 상품 상세 정보를 반환한다.")
    void getDetails() {
        // given
        Long id = 상품을_등록한다().jsonPath().getLong("id");
        // when
        var response = 상품_상세를_조회한다(id);
        // then
        상품_상세를_검증한다(response);
    }

    @Test
    @DisplayName("상품 수정 요청이 오면 상품 정보를 수정한다.")
    void modify() {
        //given
        Long id = 상품을_등록한다().jsonPath().getLong("id");
        Map<String, Object> body = new HashMap<>();
        body.put("name", "상품명 수정");
        body.put("categoryId", 2);
        body.put("price", 200000);
        body.put("content", "상품 내용을 수정");
        body.put("region", 3);
        body.put("imagesId", List.of(2, 3, 4));

        //when
        var response = RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .when().put("/api/products/{productId}", id)
                .then().log().all().extract();

        //then
        var findProduct = 상품_상세를_조회한다(id);
        Assertions.assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(response.jsonPath().getString("id")).isEqualTo("1"),
                () -> assertThat(response.jsonPath().getString("productName")).isEqualTo("상품명 수정"),
                () -> assertThat(response.jsonPath().getString("content")).isEqualTo("상품 내용을 수정"),
                () -> assertThat(response.jsonPath().getInt("price")).isEqualTo(200000)
        );
    }
}
