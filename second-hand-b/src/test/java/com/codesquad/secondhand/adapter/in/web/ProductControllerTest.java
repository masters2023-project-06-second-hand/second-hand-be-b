package com.codesquad.secondhand.adapter.in.web;

import static com.codesquad.secondhand.adapter.in.web.AcceptanceSteps.상품등록을_검증한다;
import static com.codesquad.secondhand.adapter.in.web.AcceptanceSteps.상품상세를_조회한다;
import static com.codesquad.secondhand.adapter.in.web.AcceptanceSteps.상품상세조회를_검증한다;
import static com.codesquad.secondhand.adapter.in.web.AcceptanceSteps.상품상태를_수정한다;
import static com.codesquad.secondhand.adapter.in.web.AcceptanceSteps.상품상태수정을_검증한다;
import static com.codesquad.secondhand.adapter.in.web.AcceptanceSteps.상품수정을_검증한다;
import static com.codesquad.secondhand.adapter.in.web.AcceptanceSteps.상품을_등록한다;
import static com.codesquad.secondhand.adapter.in.web.AcceptanceSteps.상품을_수정한다;

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

    @Test
    @DisplayName("상품 상세 조회 요청이 오면 상품 상세 정보를 반환한다.")
    void getDetails() {
        // given
        Long id = 상품을_등록한다().jsonPath().getLong("id");
        // when
        var response = 상품상세를_조회한다(id);
        // then
        상품상세조회를_검증한다(response);
    }

    @Test
    @DisplayName("상품 수정 요청이 오면 상품 정보를 수정한다.")
    void modify() {
        //given
        Long id = 상품을_등록한다().jsonPath().getLong("id");

        // when
        var response = 상품을_수정한다(id);

        //then
        상품수정을_검증한다(id, response);
    }

    @Test
    @DisplayName("상품 상태 수정 요청이 오면 상품 상태를 수정한다.")
    void modifyStatus() {
        //given
        Long id = 상품을_등록한다().jsonPath().getLong("id");

        //when
        var response = 상품상태를_수정한다(id);

        //then
        상품상태수정을_검증한다(id, response);
    }
}
