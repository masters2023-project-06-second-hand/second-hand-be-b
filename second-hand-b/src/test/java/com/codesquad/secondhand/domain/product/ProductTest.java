package com.codesquad.secondhand.domain.product;

import static org.assertj.core.api.Assertions.assertThat;

import com.codesquad.secondhand.domain.image.Image;
import com.codesquad.secondhand.domain.member.Member;
import com.codesquad.secondhand.domain.member.Role;
import com.codesquad.secondhand.domain.region.Region;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

class ProductTest {

    public static final String TEST_NAME = "testName";
    public static final String TEST_CONTENT = "testContent";
    public static final int TEST_PRICE = 1000;
    public static final String TEST_THUMBNAIL_IMG_URL = "url";
    public static final String TEST_CATEGORY_URL = "categoryUrl";
    public static final String TEST_CATEGORY = "testCategory";
    public static final String IMG_URL_1 = "url1";
    public static final String IMG_URL_2 = "url2";
    public static final String TEST_REGION_NAME = "삼성동";
    public static final String MODIFIED_CATEGORY_NAME = "modifyCategoryName";
    public static final String MODIFIED_CATEGORY_NAME1 = "modifiedCategoryName";
    public static final String MODIFIED_IMG_URL_1 = "modifiedImgUrl1";
    public static final String MODIFIED_IMG_URL_2 = "modifiedImgUrl2";
    public static final String MODIFIED_REGION_NAME = "modifiedRegionName";
    public static final String MODIFIED_PRODUCT_NAME = "modifiedProductName";
    public static final String MODIFIED_PRODUCT_CONTENT = "modifiedProductContent";
    public static final int MODIFIED_PRODUCT_PRICE = 2000;
    public static final String MODIFIED_THUMBNAIL_URL = "modifiedThumbnailUrl";

    @DisplayName("제품을 만들고 정보 수정 시 제품이 수정된다")
    @Test
    void modifyProduct() {
        // given
        Product product = createTestProduct();
        Category modifiedCategory = new Category(MODIFIED_CATEGORY_NAME, MODIFIED_CATEGORY_NAME1);
        Image image1 = new Image(MODIFIED_IMG_URL_1);
        Image image2 = new Image(MODIFIED_IMG_URL_2);
        List<Image> modifiedImages = List.of(image1, image2);
        Region modifiedRegion = new Region(MODIFIED_REGION_NAME);

        // when
        product.modifyProduct(MODIFIED_PRODUCT_NAME, MODIFIED_PRODUCT_CONTENT, MODIFIED_PRODUCT_PRICE, modifiedCategory,
                MODIFIED_THUMBNAIL_URL, modifiedImages, modifiedRegion);

        // then
        Assertions.assertAll(
                () -> assertThat(product.getName()).isEqualTo(MODIFIED_PRODUCT_NAME),
                () -> assertThat(product.getContent()).isEqualTo(MODIFIED_PRODUCT_CONTENT),
                () -> assertThat(product.getPrice()).isEqualTo(MODIFIED_PRODUCT_PRICE),
                () -> assertThat(product.getCategory()).isEqualTo(modifiedCategory),
                () -> assertThat(product.getThumbnailUrl()).isEqualTo(MODIFIED_THUMBNAIL_URL),
                () -> assertThat(product.fetchImages()).usingRecursiveComparison()
                        .isEqualTo(new Images(modifiedImages).getImageList()),
                () -> assertThat(product.getRegion()).isEqualTo(modifiedRegion)
        );
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
        Image image1 = new Image(IMG_URL_1);
        Image image2 = new Image(IMG_URL_2);
        List<Image> images = List.of(image1, image2);

        // when
        List<Image> targetImages = product.fetchImages();

        // then
        assertThat(targetImages).usingRecursiveComparison().isEqualTo(images);
    }

    private static Product createTestProduct() {
        Image image1 = new Image(IMG_URL_1);
        Image image2 = new Image(IMG_URL_2);
        List<Image> images = List.of(image1, image2);
        Region region = new Region(TEST_REGION_NAME);
        LocalDateTime YESTERDAY = LocalDateTime.now().minusDays(1);
        Member testWriter = new Member(
                "test@email.com",
                "testNickName",
                "testProfileImage",
                region,
                Role.MEMBER);
        Category testCategory = new Category(TEST_CATEGORY, TEST_CATEGORY_URL);
        return new Product(
                TEST_NAME,
                TEST_CONTENT,
                TEST_PRICE,
                testWriter,
                testCategory,
                TEST_THUMBNAIL_IMG_URL,
                images,
                region,
                Status.ONSALES,
                YESTERDAY
        );
    }
}
