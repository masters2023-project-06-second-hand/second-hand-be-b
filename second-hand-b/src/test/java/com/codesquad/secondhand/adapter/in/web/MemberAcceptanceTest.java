package com.codesquad.secondhand.adapter.in.web;

import static com.codesquad.secondhand.adapter.in.web.MemberSteps.관심상품에_담는다;
import static com.codesquad.secondhand.adapter.in.web.MemberSteps.관심상품에_제거한다;
import static com.codesquad.secondhand.adapter.in.web.MemberSteps.관심상품은_담은_응답_검증;
import static com.codesquad.secondhand.adapter.in.web.MemberSteps.나의_관심상품_목록_조회_검증한다;
import static com.codesquad.secondhand.adapter.in.web.MemberSteps.나의_관심상품_목록_조회한다;
import static com.codesquad.secondhand.adapter.in.web.MemberSteps.나의_관심상품의_카테고리_목록_조회_결과_검증한다;
import static com.codesquad.secondhand.adapter.in.web.MemberSteps.나의_관심상품의_카테고리_목록_조회한다;
import static com.codesquad.secondhand.adapter.in.web.MemberSteps.나의_상태별_판매상품의_목록_조회_결과_검증한다;
import static com.codesquad.secondhand.adapter.in.web.MemberSteps.나의_카테고리별_관심상품_목록_조회_결과_검증한다;
import static com.codesquad.secondhand.adapter.in.web.MemberSteps.나의_판매상품_목록_조회한다;
import static com.codesquad.secondhand.adapter.in.web.MemberSteps.나의_판매상품_목록을_상태별_조회한다;
import static com.codesquad.secondhand.adapter.in.web.MemberSteps.나의_판매상품의_목록_조회_결과_검증한다;
import static com.codesquad.secondhand.adapter.in.web.ProductSteps.상품상태를_수정한다;
import static com.codesquad.secondhand.adapter.in.web.ProductSteps.상품을_등록한다;

import com.codesquad.secondhand.utils.AcceptanceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MemberAcceptanceTest extends AcceptanceTest {

    @DisplayName("특정 상품을 내 관심상품에 추가")
    @Test
    void shouldAddProductToInterestedProductsList() {
        // given
        var id = 상품을_등록한다(ayaanAccessToken, 1).jsonPath().getString("id");

        // when
        var response = 관심상품에_담는다(id, albertAccessToken);

        // then
        관심상품은_담은_응답_검증(response);
    }

    @DisplayName("내 관심상품에 담은 후, 해당 관심상품 삭제")
    @Test
    void shouldRemoveProductToInterestedProductsList() {
        // given
        var id = 상품을_등록한다(ayaanAccessToken, 1).jsonPath().getString("id");
        관심상품에_담는다(id, albertAccessToken);

        // when
        var response = 관심상품에_제거한다(id, albertAccessToken);

        // then
        관심상품은_담은_응답_검증(response);
    }

    @DisplayName("관심상품 목록 조회 요청 시, 목록 반환")
    @Test
    void shouldReturnListOfInterestedProductsWhenRequested() {
        // given
        var 상품_아이디_1 = 상품을_등록한다(ayaanAccessToken, 1).jsonPath().getString("id");
        var 상품_아이디_2 = 상품을_등록한다(ayaanAccessToken, 2).jsonPath().getString("id");
        관심상품에_담는다(상품_아이디_1, albertAccessToken);
        관심상품에_담는다(상품_아이디_2, albertAccessToken);

        // when
        var response = 나의_관심상품_목록_조회한다(albertAccessToken);

        // then
        나의_관심상품_목록_조회_검증한다(response);
    }

    @DisplayName("관심상품의 카테고리별 목록 조회 요청 시, 카테고리별 목록 반환")
    @Test
    void shouldReturnProductsGroupedByCategoryWhenFetchingFavorites() {
        // given
        var 상품_아이디_1 = 상품을_등록한다(ayaanAccessToken, 1).jsonPath().getString("id");
        var 상품_아이디_2 = 상품을_등록한다(ayaanAccessToken, 2).jsonPath().getString("id");
        관심상품에_담는다(상품_아이디_1, albertAccessToken);
        관심상품에_담는다(상품_아이디_2, albertAccessToken);

        // when
        var response = 나의_관심상품_목록_조회한다(albertAccessToken, 1);

        // then
        나의_카테고리별_관심상품_목록_조회_결과_검증한다(response);
    }

    @DisplayName("나의 관심상품의 카테고리 목록 조회 요청 시, 카테고리 목록 반환")
    @Test
    void shouldReturnCategoriesOfInterestedProductsWhenRequested() {
        // given
        var 상품_아이디_1 = 상품을_등록한다(ayaanAccessToken, 1).jsonPath().getString("id");
        var 상품_아이디_2 = 상품을_등록한다(ayaanAccessToken, 2).jsonPath().getString("id");
        관심상품에_담는다(상품_아이디_1, albertAccessToken);
        관심상품에_담는다(상품_아이디_2, albertAccessToken);

        // when
        var response = 나의_관심상품의_카테고리_목록_조회한다(albertAccessToken);

        // then
        나의_관심상품의_카테고리_목록_조회_결과_검증한다(response);
    }

    @DisplayName("판매상품 목록 조회 요청 시, 내 판매 상품 반환")
    @Test
    void shouldReturnMySellingProductsWhenRequested() {
        // given
        상품을_등록한다(ayaanAccessToken, 1);
        상품을_등록한다(ayaanAccessToken, 2);

        // when
        var response = 나의_판매상품_목록_조회한다(ayaanAccessToken);

        // then
        나의_판매상품의_목록_조회_결과_검증한다(response);
    }

    @DisplayName("상태별 판매상품 목록 조회 시, 내 상품 목록 반환")
    @Test
    void shouldReturnMyProductsByStatusWhenRequested() {
        // given
        상품을_등록한다(ayaanAccessToken, 1);
        long id = 상품을_등록한다(ayaanAccessToken, 2).jsonPath().getLong("id");
        상품상태를_수정한다(id, ayaanAccessToken);

        // when
        var response = 나의_판매상품_목록을_상태별_조회한다(ayaanAccessToken, "판매중");

        // then
        나의_상태별_판매상품의_목록_조회_결과_검증한다(response);
    }
}
