package com.codesquad.secondhand.domain.product;

import com.codesquad.secondhand.domain.image.Image;
import com.codesquad.secondhand.domain.member.Member;
import com.codesquad.secondhand.domain.member.Role;
import com.codesquad.secondhand.domain.region.Region;
import java.time.LocalDateTime;
import java.util.List;

public class ProductTestUtils {

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
    public static final String TEST_EMAIL = "test@email.com";
    public static final String TEST_NICK_NAME = "testNickName";
    public static final String TEST_PROFILE_IMAGE = "testProfileImage";

    public static Product createTestProduct() {
        List<Image> images = getDefaultTestImages();
        Region region = getDefaultTestRegion();
        LocalDateTime testCreatedTime = getDefaultTestCreatedTime();
        Category testCategory = getDefaultTestCategory();
        return new Product(
                TEST_NAME,
                TEST_CONTENT,
                TEST_PRICE,
                0,
                testCategory,
                TEST_THUMBNAIL_IMG_URL,
                images,
                region,
                getTestDefaultStatus(),
                testCreatedTime
        );
    }

    public static Status getTestDefaultStatus() {
        return Status.ON_SALES;
    }

    public static Region getDefaultTestRegion() {
        return new Region(TEST_REGION_NAME);
    }

    public static LocalDateTime getDefaultTestCreatedTime() {
        return LocalDateTime.of(2023, 9, 13, 12, 10);
    }

    public static Member getDefaultTestWriter() {
        return new Member(
                TEST_EMAIL,
                TEST_NICK_NAME,
                TEST_PROFILE_IMAGE,
                Role.MEMBER);
    }

    public static Category getDefaultTestCategory() {
        return new Category(TEST_CATEGORY, TEST_CATEGORY_URL);
    }


    public static List<Image> getDefaultTestImages() {
        Image image1 = new Image(IMG_URL_1);
        Image image2 = new Image(IMG_URL_2);
        return List.of(image1, image2);
    }
}
