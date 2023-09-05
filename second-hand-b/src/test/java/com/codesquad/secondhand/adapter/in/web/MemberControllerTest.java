package com.codesquad.secondhand.adapter.in.web;

import static com.codesquad.secondhand.adapter.in.web.ProductSteps.상품을_등록한다;
import static org.assertj.core.api.Assertions.assertThat;

import com.codesquad.secondhand.utils.AcceptanceTest;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

class MemberControllerTest extends AcceptanceTest {

    @DisplayName("특정 상품에 대하여 내 관삼상품에 담는다.")
    @Test
    void shouldAddProductToInterestedProductsList() {
        // given
        var id = 상품을_등록한다(ayaanAccessToken).jsonPath().getString("id");

        // when
        Map<String, Boolean> body = new HashMap<>();
        body.put("isLiked", true);
        var response = RestAssured.given().log().all()
                .auth().oauth2(albertAccessToken)
                .body(body).contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().put("/api/products/{productId}/likes", id)
                .then().log().all()
                .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }


    @DisplayName("나의 관삼상품을 목록을 조회 요청하면 목록을 보여준다")
    @Test
    void shouldReturnListOfInterestedProductsWhenRequested() {
        // given

        // when
        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .when().get("/api/members/{memberId}/likes", 1)
                .then().log().all()
                .extract();
    }
}
