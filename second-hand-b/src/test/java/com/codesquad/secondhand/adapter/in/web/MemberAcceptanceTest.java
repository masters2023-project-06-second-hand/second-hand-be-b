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
import static com.codesquad.secondhand.adapter.in.web.MemberSteps.멤버의_정보를_요청한다;
import static com.codesquad.secondhand.adapter.in.web.MemberSteps.멤버정보_요청을_검증한다;
import static com.codesquad.secondhand.adapter.in.web.ProductSteps.상품상태를_수정한다;
import static com.codesquad.secondhand.adapter.in.web.ProductSteps.상품을_등록한다;
import static com.codesquad.secondhand.utils.RestDocsUtils.출력_필드_추가;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.codesquad.secondhand.utils.AcceptanceTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MemberAcceptanceTest extends AcceptanceTest {

    @BeforeEach
    public void setS3StorageService() {
        when(s3StorageService.upload(any())).thenReturn("testUrl");
    }

    @DisplayName("특정 상품을 내 관심상품에 추가")
    @Test
    void shouldAddProductToInterestedProductsList() {
        출력_필드_추가("member_addLikeProduct", spec);

        // given
        var id = 상품을_등록한다(ayaanAccessToken, 1).jsonPath().getString("id");

        // when
        var response = 관심상품에_담는다(id, albertAccessToken, spec);

        // then
        관심상품은_담은_응답_검증(response);
    }

    @DisplayName("내 관심상품에 담은 후, 해당 관심상품 삭제")
    @Test
    void shouldRemoveProductToInterestedProductsList() {
        출력_필드_추가("member_removeLikeProduct", spec);

        // given
        var id = 상품을_등록한다(ayaanAccessToken, 1).jsonPath().getString("id");
        관심상품에_담는다(id, albertAccessToken);

        // when
        var response = 관심상품에_제거한다(id, albertAccessToken, spec);

        // then
        관심상품은_담은_응답_검증(response);
    }

    @DisplayName("관심상품 목록 조회 요청 시, 목록 반환")
    @Test
    void shouldReturnListOfInterestedProductsWhenRequested() {
        출력_필드_추가("member_getLikeProducts", spec);

        // given
        var 상품_아이디_1 = 상품을_등록한다(ayaanAccessToken, 1).jsonPath().getString("id");
        var 상품_아이디_2 = 상품을_등록한다(ayaanAccessToken, 2).jsonPath().getString("id");
        관심상품에_담는다(상품_아이디_1, albertAccessToken);
        관심상품에_담는다(상품_아이디_2, albertAccessToken);

        // when
        var response = 나의_관심상품_목록_조회한다(albertAccessToken, spec);

        // then
        나의_관심상품_목록_조회_검증한다(response);
    }

    @DisplayName("관심상품의 카테고리별 목록 조회 요청 시, 카테고리별 목록 반환")
    @Test
    void shouldReturnProductsGroupedByCategoryWhenFetchingFavorites() {
        출력_필드_추가("member_getLikeProductsByCategory", spec);

        // given
        var 상품_아이디_1 = 상품을_등록한다(ayaanAccessToken, 1).jsonPath().getString("id");
        var 상품_아이디_2 = 상품을_등록한다(ayaanAccessToken, 2).jsonPath().getString("id");
        관심상품에_담는다(상품_아이디_1, albertAccessToken);
        관심상품에_담는다(상품_아이디_2, albertAccessToken);

        // when
        var response = 나의_관심상품_목록_조회한다(albertAccessToken, 1, spec);

        // then
        나의_카테고리별_관심상품_목록_조회_결과_검증한다(response);
    }

    @DisplayName("나의 관심상품의 카테고리 목록 조회 요청 시, 카테고리 목록 반환")
    @Test
    void shouldReturnCategoriesOfInterestedProductsWhenRequested() {
        출력_필드_추가("member_getLikeCategory", spec);

        // given
        var 상품_아이디_1 = 상품을_등록한다(ayaanAccessToken, 1).jsonPath().getString("id");
        var 상품_아이디_2 = 상품을_등록한다(ayaanAccessToken, 2).jsonPath().getString("id");
        관심상품에_담는다(상품_아이디_1, albertAccessToken);
        관심상품에_담는다(상품_아이디_2, albertAccessToken);

        // when
        var response = 나의_관심상품의_카테고리_목록_조회한다(albertAccessToken, spec);

        // then
        나의_관심상품의_카테고리_목록_조회_결과_검증한다(response);
    }

    @DisplayName("판매상품 목록 조회 요청 시, 내 상품 목록 반환")
    @Test
    void shouldReturnMySellingProductsWhenRequested() {
        출력_필드_추가("member_getSalesProducts", spec);

        // given
        상품을_등록한다(ayaanAccessToken, 1);
        상품을_등록한다(ayaanAccessToken, 2);

        // when
        var response = 나의_판매상품_목록_조회한다(ayaanAccessToken, spec);

        // then
        나의_판매상품의_목록_조회_결과_검증한다(response);
    }

    @DisplayName("상태별 판매상품 목록 조회 시, 내 상품 목록 반환")
    @Test
    void shouldReturnMyProductsByStatusWhenRequested() {
        출력_필드_추가("member_getSalesProductsByStatus", spec);

        // given
        상품을_등록한다(ayaanAccessToken, 1);
        long id = 상품을_등록한다(ayaanAccessToken, 2).jsonPath().getLong("id");
        상품상태를_수정한다(id, ayaanAccessToken);

        // when
        var response = 나의_판매상품_목록을_상태별_조회한다(ayaanAccessToken, "판매중", spec);

        // then
        나의_상태별_판매상품의_목록_조회_결과_검증한다(response);
    }

    @Test
    @DisplayName("멤버의 정보 요청 시, 멤버의 정보 반환")
    void getProfile() {
        출력_필드_추가("member_getProfile", spec);

        // given
        Long memberId = 1L;

        // when
        var response = 멤버의_정보를_요청한다(memberId, ayaanAccessToken, spec);

        // then
        멤버정보_요청을_검증한다(response);
    }
}
