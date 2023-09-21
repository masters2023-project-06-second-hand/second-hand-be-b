package com.codesquad.secondhand.adapter.in.web;

import static com.codesquad.secondhand.adapter.in.web.ProductSteps.regionId로_상품목록을_조회한다;
import static com.codesquad.secondhand.adapter.in.web.ProductSteps.regionId와_categoryId로_지역목록을_조회한다;
import static com.codesquad.secondhand.adapter.in.web.ProductSteps.regionid로_조회된_상품몰록_검증;
import static com.codesquad.secondhand.adapter.in.web.ProductSteps.regionid와_categoryId로_조회된_지역목록_검증;
import static com.codesquad.secondhand.adapter.in.web.ProductSteps.상품등록을_검증한다;
import static com.codesquad.secondhand.adapter.in.web.ProductSteps.상품상세를_조회한다;
import static com.codesquad.secondhand.adapter.in.web.ProductSteps.상품상세조회를_검증한다;
import static com.codesquad.secondhand.adapter.in.web.ProductSteps.상품상태를_수정한다;
import static com.codesquad.secondhand.adapter.in.web.ProductSteps.상품상태수정을_검증한다;
import static com.codesquad.secondhand.adapter.in.web.ProductSteps.상품수정을_검증한다;
import static com.codesquad.secondhand.adapter.in.web.ProductSteps.상품을_등록한다;
import static com.codesquad.secondhand.adapter.in.web.ProductSteps.상품을_삭제한다;
import static com.codesquad.secondhand.adapter.in.web.ProductSteps.상품을_수정한다;
import static com.codesquad.secondhand.adapter.in.web.ProductSteps.잘못된_상품_등록요청;
import static com.codesquad.secondhand.adapter.in.web.ProductSteps.잘못된_상품등록_응답_검증한다;
import static com.codesquad.secondhand.adapter.in.web.ProductSteps.잘못된_상품수정_요청을_검증한다;
import static com.codesquad.secondhand.adapter.in.web.ProductSteps.잘못된_요청으로_상품을_수정한다;
import static com.codesquad.secondhand.utils.RestDocsUtils.출력_필드_추가;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.codesquad.secondhand.utils.AcceptanceTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

class ProductAcceptanceTest extends AcceptanceTest {

    @BeforeEach
    public void setS3StorageService() {
        when(s3StorageService.upload(any())).thenReturn("testUrl");
    }

    @DisplayName("상품 등록 요청이 오면 상품 아이디를 반환한다.")
    @Test
    void create() {
        출력_필드_추가("product_create", spec);

        var response = 상품을_등록한다(ayaanAccessToken, 1, spec);

        상품등록을_검증한다(response);
    }

    @DisplayName("잘못된 상품 등록 요청이 에러 메시지를 반환한다.")
    @Test
    void createWithErrorRequest() {
        출력_필드_추가("product_createWithErrorRequest", spec);

        var response = 잘못된_상품_등록요청(ayaanAccessToken, spec);

        잘못된_상품등록_응답_검증한다(response);
    }

    @DisplayName("상품 상세 조회 요청이 오면 상품 상세 정보를 반환한다.")
    @Test
    void getDetails() {
        출력_필드_추가("product_getDetails", spec);
        // given
        Long id = 상품을_등록한다(ayaanAccessToken, 1).jsonPath().getLong("id");
        // when
        var response = 상품상세를_조회한다(id, ayaanAccessToken, spec);
        // then
        상품상세조회를_검증한다(response);
    }

    @DisplayName("상품 수정 요청이 오면 상품 정보를 수정한다.")
    @Test
    void modify() {
        출력_필드_추가("product_modify", spec);

        //given
        Long id = 상품을_등록한다(ayaanAccessToken, 1).jsonPath().getLong("id");

        // when
        var response = 상품을_수정한다(id, ayaanAccessToken, spec);

        //then
        상품수정을_검증한다(id, ayaanAccessToken, response);
    }

    @DisplayName("잘못된 상품 수정 요청이 오면 에러 메시지를 반환한다")
    @Test
    void modifyWithWrongRequest() {
        출력_필드_추가("product_modifyWithWrongRequest", spec);

        // when
        var response = 잘못된_요청으로_상품을_수정한다(ayaanAccessToken, spec);

        //then
        잘못된_상품수정_요청을_검증한다(response);
    }

    @DisplayName("상품 상태 수정 요청이 오면 상품 상태를 수정한다.")
    @Test
    void modifyStatus() {
        출력_필드_추가("product_modifyStatus", spec);

        //given
        Long id = 상품을_등록한다(ayaanAccessToken, 1).jsonPath().getLong("id");

        //when
        var response = 상품상태를_수정한다(id, ayaanAccessToken, spec);

        //then
        상품상태수정을_검증한다(id, ayaanAccessToken, response);
    }

    @DisplayName("지역id에 해당하는 상품 목록을 조회하여 반환한다.")
    @Test
    void getProductListByRegion() {
        출력_필드_추가("product_getProductListByRegion", spec);

        //given
        상품을_등록한다(ayaanAccessToken, 1);
        상품을_등록한다(ayaanAccessToken, 2);
        Long regionId = 1L;

        //when
        var response = regionId로_상품목록을_조회한다(regionId, ayaanAccessToken, spec);

        //then
        regionid로_조회된_상품몰록_검증(response);
    }

    @DisplayName("지역id와 카테고리id에 해당하는 상품 목록을 조회하여 반환한다.")
    @Test
    void getProductListByRegionAndCategory() {
        출력_필드_추가("product_getProductListByRegionAndCategory", spec);

        //given
        상품을_등록한다(ayaanAccessToken, 1);
        상품을_등록한다(ayaanAccessToken, 2);
        상품을_등록한다(ayaanAccessToken, 2);
        Long regionId = 1L;
        Long categoryId = 2L;

        //when
        var response = regionId와_categoryId로_지역목록을_조회한다(regionId, categoryId, ayaanAccessToken, spec);

        //then
        regionid와_categoryId로_조회된_지역목록_검증(response);
    }

    @DisplayName("상품 삭제 요청을 받으면 요청을 수행하고 204 상태코드로 응답한다.")
    @Test
    void deleteProduct() {
        출력_필드_추가("product_deleteProduct", spec);

        //given
        long productId = 상품을_등록한다(ayaanAccessToken, 1).jsonPath().getLong("id");

        //when
        var response = 상품을_삭제한다(productId, ayaanAccessToken, spec);

        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }
}
