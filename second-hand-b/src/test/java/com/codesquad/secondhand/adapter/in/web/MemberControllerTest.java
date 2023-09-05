package com.codesquad.secondhand.adapter.in.web;

import static com.codesquad.secondhand.adapter.in.web.MemberSteps.관심상품에_담는다;
import static com.codesquad.secondhand.adapter.in.web.MemberSteps.관심상품에_제거한다;
import static com.codesquad.secondhand.adapter.in.web.MemberSteps.관심상품은_담은_응답_검증;
import static com.codesquad.secondhand.adapter.in.web.MemberSteps.나의_관심상품_목록_조회_검증한다;
import static com.codesquad.secondhand.adapter.in.web.MemberSteps.나의_광심상품_목록_조회한다;
import static com.codesquad.secondhand.adapter.in.web.MemberSteps.나의_카테고리별_관심상품_목록_조회_검증한다;
import static com.codesquad.secondhand.adapter.in.web.ProductSteps.상품을_등록한다;

import com.codesquad.secondhand.utils.AcceptanceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MemberControllerTest extends AcceptanceTest {

    @DisplayName("특정 상품에 대하여 내 관심상품에 담는다.")
    @Test
    void shouldAddProductToInterestedProductsList() {
        // given
        var id = 상품을_등록한다(ayaanAccessToken, 1).jsonPath().getString("id");

        // when
        var response = 관심상품에_담는다(id, albertAccessToken);

        // then
        관심상품은_담은_응답_검증(response);
    }

    @DisplayName("내 관심상품에 담고 낸 관심상품에 삭제한다.")
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

    @DisplayName("나의 관심상품을 목록을 조회 요청하면 목록을 보여준다")
    @Test
    void shouldReturnListOfInterestedProductsWhenRequested() {
        // given
        var 상품_아이디_1 = 상품을_등록한다(ayaanAccessToken, 1).jsonPath().getString("id");
        var 상품_아이디_2 = 상품을_등록한다(ayaanAccessToken, 2).jsonPath().getString("id");
        관심상품에_담는다(상품_아이디_1, albertAccessToken);
        관심상품에_담는다(상품_아이디_2, albertAccessToken);

        // when
        var response = 나의_광심상품_목록_조회한다(albertAccessToken);

        // then
        나의_관심상품_목록_조회_검증한다(response);
    }

    @DisplayName("나의 관심상품을 카테고리 별 목록을 조회 요청하면 카테고리 별 목록을 보여준다")
    @Test
    void shouldReturnProductsGroupedByCategoryWhenFetchingFavorites() {
        // given
        var 상품_아이디_1 = 상품을_등록한다(ayaanAccessToken, 1).jsonPath().getString("id");
        var 상품_아이디_2 = 상품을_등록한다(ayaanAccessToken, 2).jsonPath().getString("id");
        관심상품에_담는다(상품_아이디_1, albertAccessToken);
        관심상품에_담는다(상품_아이디_2, albertAccessToken);

        // when
        var response = 나의_광심상품_목록_조회한다(albertAccessToken, 1);

        // then
        나의_카테고리별_관심상품_목록_조회_검증한다(response);
    }
}
