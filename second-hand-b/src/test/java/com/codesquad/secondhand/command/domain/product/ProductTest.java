package com.codesquad.secondhand.command.domain.product;

import static com.codesquad.secondhand.command.domain.product.ProductTestUtils.MODIFIED_CATEGORY_NAME;
import static com.codesquad.secondhand.command.domain.product.ProductTestUtils.MODIFIED_CATEGORY_NAME1;
import static com.codesquad.secondhand.command.domain.product.ProductTestUtils.MODIFIED_IMG_URL_1;
import static com.codesquad.secondhand.command.domain.product.ProductTestUtils.MODIFIED_IMG_URL_2;
import static com.codesquad.secondhand.command.domain.product.ProductTestUtils.MODIFIED_PRODUCT_CONTENT;
import static com.codesquad.secondhand.command.domain.product.ProductTestUtils.MODIFIED_PRODUCT_NAME;
import static com.codesquad.secondhand.command.domain.product.ProductTestUtils.MODIFIED_PRODUCT_PRICE;
import static com.codesquad.secondhand.command.domain.product.ProductTestUtils.MODIFIED_THUMBNAIL_URL;
import static com.codesquad.secondhand.command.domain.product.ProductTestUtils.createTestProduct;
import static com.codesquad.secondhand.command.domain.product.ProductTestUtils.getDefaultTestImages;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

class ProductTest {

    @DisplayName("제품을 만들고 정보 수정 시 제품이 수정된다")
    @Test
    void modifyProduct() {
        // given
        Product product = createTestProduct();

        // when
        modifiedProduct(product);

        // then
        validateModifiedProduct(product);
    }

    @DisplayName("제품을 생성하고 그 Status를 수정하면 수정된다")
    @ParameterizedTest
    @EnumSource(Status.class)
    void modifyStatus(Status status) {
        // given
        Product product = new Product();

        // when
        product.modifyStatus(status.getName());

        // then
        assertThat(product.getStatus()).isEqualTo(status);
    }

    @DisplayName("이미지를 가져오면 저장한 이미지랑 같다")
    @Test
    void fetchImageInfos() {
        // given
        Product product = createTestProduct();

        // when
        List<Image> targetImages = product.fetchImages();

        // then
        validateProductFetchImage(targetImages);
    }

    public static void modifiedProduct(Product product) {
        Category modifiedCategory = getModifiedCategory();
        List<Image> modifiedImages = getModifiedImages();
        product.modifyProduct(MODIFIED_PRODUCT_NAME, MODIFIED_PRODUCT_CONTENT, MODIFIED_PRODUCT_PRICE,
                modifiedCategory,
                MODIFIED_THUMBNAIL_URL, modifiedImages, 1);
    }

    private static Category getModifiedCategory() {
        return new Category(MODIFIED_CATEGORY_NAME, MODIFIED_CATEGORY_NAME1);
    }

    public static List<Image> getModifiedImages() {
        Image image1 = new Image(MODIFIED_IMG_URL_1);
        Image image2 = new Image(MODIFIED_IMG_URL_2);
        return List.of(image1, image2);
    }

    public static void validateModifiedProduct(Product product) {
        Assertions.assertAll(
                () -> assertThat(product.getName()).isEqualTo(MODIFIED_PRODUCT_NAME),
                () -> assertThat(product.getContent()).isEqualTo(MODIFIED_PRODUCT_CONTENT),
                () -> assertThat(product.getPrice()).isEqualTo(MODIFIED_PRODUCT_PRICE),
                () -> assertThat(product.getCategory()).usingRecursiveComparison().isEqualTo(getModifiedCategory()),
                () -> assertThat(product.getThumbnailUrl()).isEqualTo(MODIFIED_THUMBNAIL_URL),
                () -> assertThat(product.fetchImages()).usingRecursiveComparison().isEqualTo(getModifiedImages()),
                () -> assertThat(product.getRegionId()).isEqualTo(1)
        );
    }

    public static void validateProductFetchImage(List<Image> targetImages) {
        List<Image> images = getDefaultTestImages();
        assertThat(targetImages).usingRecursiveComparison().isEqualTo(images);
    }
}
