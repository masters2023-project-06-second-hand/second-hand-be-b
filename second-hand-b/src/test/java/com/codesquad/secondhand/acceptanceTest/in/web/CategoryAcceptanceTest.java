package com.codesquad.secondhand.acceptanceTest.in.web;

import static com.codesquad.secondhand.acceptanceTest.in.web.CategorySteps.이미지포함_카테고리_목록조회를_검증한다;
import static com.codesquad.secondhand.acceptanceTest.in.web.CategorySteps.카테고리_목록을_조회한다;
import static com.codesquad.secondhand.acceptanceTest.in.web.CategorySteps.카테고리_목록조회를_검증한다;
import static com.codesquad.secondhand.utils.RestDocsUtils.출력_필드_추가;

import com.codesquad.secondhand.utils.AcceptanceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CategoryAcceptanceTest extends AcceptanceTest {

    @Test
    @DisplayName("카테고리 목록 조회 요청을 받으면 카테고리 목록을 반환한다.")
    void getCategories() {
        출력_필드_추가("category_getCategories", spec);

        //given
        boolean includeImages = false;

        //when
        var response = 카테고리_목록을_조회한다(includeImages, ayaanAccessToken, spec);

        //then
        카테고리_목록조회를_검증한다(response);
    }

    @Test
    @DisplayName("카테고리 목록 조회 요청을 받으면 includeImages가 true인 경우 imgUrl을 포함하는 카테고리 목록을 반환한다.")
    void getCategoriesWithImgUrl() {
        출력_필드_추가("category_getCategoriesWithImg", spec);

        //given
        boolean includeImages = true;

        //when
        var response = 카테고리_목록을_조회한다(includeImages, ayaanAccessToken, spec);

        //then
        이미지포함_카테고리_목록조회를_검증한다(response);
    }
}
