package com.codesquad.secondhand.adapter.in.web;

import static com.codesquad.secondhand.adapter.in.web.ProductSteps.regionId로_상품목록을_조회한다;
import static com.codesquad.secondhand.adapter.in.web.ProductSteps.regionId와_categoryId로_지역목록을_조회한다;
import static com.codesquad.secondhand.adapter.in.web.ProductSteps.상품등록을_검증한다;
import static com.codesquad.secondhand.adapter.in.web.ProductSteps.상품상세를_조회한다;
import static com.codesquad.secondhand.adapter.in.web.ProductSteps.상품상세조회를_검증한다;
import static com.codesquad.secondhand.adapter.in.web.ProductSteps.상품상태를_수정한다;
import static com.codesquad.secondhand.adapter.in.web.ProductSteps.상품상태수정을_검증한다;
import static com.codesquad.secondhand.adapter.in.web.ProductSteps.상품수정을_검증한다;
import static com.codesquad.secondhand.adapter.in.web.ProductSteps.상품을_등록한다;
import static com.codesquad.secondhand.adapter.in.web.ProductSteps.상품을_삭제한다;
import static com.codesquad.secondhand.adapter.in.web.ProductSteps.상품을_수정한다;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.codesquad.secondhand.utils.AcceptanceTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

class ProductAcceptanceTest extends AcceptanceTest {

    @BeforeEach
    public void setS3StorageService() {
        when(s3StorageService.upload(any())).thenReturn("testUrl");
    }

    @Test
    @DisplayName("상품 등록 요청이 오면 상품 아이디를 반환한다.")
    void create() {
        var response = 상품을_등록한다(ayaanAccessToken, 1);

        상품등록을_검증한다(response);
    }

    @Test
    @DisplayName("상품 상세 조회 요청이 오면 상품 상세 정보를 반환한다.")
    void getDetails() {
        // given
        Long id = 상품을_등록한다(ayaanAccessToken, 1).jsonPath().getLong("id");
        // when
        var response = 상품상세를_조회한다(id, ayaanAccessToken);
        // then
        상품상세조회를_검증한다(response);
    }

    @Test
    @DisplayName("상품 수정 요청이 오면 상품 정보를 수정한다.")
    void modify() {
        //given
        Long id = 상품을_등록한다(ayaanAccessToken, 1).jsonPath().getLong("id");

        // when
        var response = 상품을_수정한다(id, ayaanAccessToken);

        //then
        상품수정을_검증한다(id, ayaanAccessToken, response);
    }

    @Test
    @DisplayName("상품 상태 수정 요청이 오면 상품 상태를 수정한다.")
    void modifyStatus() {
        //given
        Long id = 상품을_등록한다(ayaanAccessToken, 1).jsonPath().getLong("id");

        //when
        var response = 상품상태를_수정한다(id, ayaanAccessToken);

        //then
        상품상태수정을_검증한다(id, ayaanAccessToken, response);
    }

    @Test
    @DisplayName("지역id에 해당하는 상품 목록을 조회하여 반환한다.")
    void getProductListByRegion() {
        //given
        상품을_등록한다(ayaanAccessToken, 1);
        상품을_등록한다(ayaanAccessToken, 2);
        Long regionId = 1L;

        //when
        var response = regionId로_상품목록을_조회한다(regionId, ayaanAccessToken);

        //then
        Assertions.assertAll(
                () -> assertThat(response.jsonPath().getList(".")).hasSize(2),
                () -> assertThat(response.jsonPath().getList("id")).containsExactly(1, 2)
        );
    }

    @Test
    @DisplayName("지역id와 카테고리id에 해당하는 상품 목록을 조회하여 반환한다.")
    void getProductListByRegionAndCategory() {
        //given
        상품을_등록한다(ayaanAccessToken, 1);
        상품을_등록한다(ayaanAccessToken, 2);
        상품을_등록한다(ayaanAccessToken, 2);
        Long regionId = 1L;
        Long categoryId = 2L;

        //when
        var response = regionId와_categoryId로_지역목록을_조회한다(regionId, categoryId, ayaanAccessToken);

        //then
        Assertions.assertAll(
                () -> assertThat(response.jsonPath().getList(".")).hasSize(2),
                () -> assertThat(response.jsonPath().getList("id")).containsExactly(2, 3)
        );
    }

    @Test
    @DisplayName("상품 삭제 요청을 받으면 요청을 수행하고 204 상태코드로 응답한다.")
    void deleteProduct() {
        //given
        long productId = 상품을_등록한다(ayaanAccessToken, 1).jsonPath().getLong("id");

        //when
        var response = 상품을_삭제한다(productId, ayaanAccessToken);

        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }
}
