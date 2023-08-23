package com.codesquad.secondhand.adapter.in.web;

import static com.codesquad.secondhand.adapter.in.web.AcceptanceSteps.상품등록을_검증한다;
import static com.codesquad.secondhand.adapter.in.web.AcceptanceSteps.상품을_등록한다;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
class ProductControllerTest {

    @Test
    @DisplayName("상품 등록 요청이 오면 상품 아이디를 반환한다.")
    void create() {
        var response = 상품을_등록한다();

        상품등록을_검증한다(response);
    }
}
