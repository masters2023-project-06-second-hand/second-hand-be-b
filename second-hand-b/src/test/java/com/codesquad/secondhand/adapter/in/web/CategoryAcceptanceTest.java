package com.codesquad.secondhand.adapter.in.web;

import static com.codesquad.secondhand.adapter.in.web.CategorySteps.이미지포함_카테고리_목록조회를_검증한다;
import static com.codesquad.secondhand.adapter.in.web.CategorySteps.카테고리_목록을_조회한다;
import static com.codesquad.secondhand.adapter.in.web.CategorySteps.카테고리_목록조회를_검증한다;

import com.codesquad.secondhand.utils.AcceptanceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CategoryAcceptanceTest extends AcceptanceTest {

    @Test
    @DisplayName("카테고리 목록 조회 요청을 받으면 카테고리 목록을 반환한다.")
    void getCategories() {
        //when
        var response = 카테고리_목록을_조회한다(false, ayaanAccessToken);

        //then
        카테고리_목록조회를_검증한다(response);
    }

    @Test
    @DisplayName("카테고리 목록 조회 요청을 받으면 includeImages가 true인 경우 imgUrl을 포함하는 카테고리 목록을 반환한다.")
    void getCategoriesWithImgUrl() {
        //when
        var response = 카테고리_목록을_조회한다(true, ayaanAccessToken);

        //then
        이미지포함_카테고리_목록조회를_검증한다(response);
    }
}
