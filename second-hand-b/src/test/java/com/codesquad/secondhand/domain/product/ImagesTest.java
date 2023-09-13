package com.codesquad.secondhand.domain.product;

import static org.assertj.core.api.Assertions.*;

import com.codesquad.secondhand.domain.image.Image;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ImagesTest {

    public static final String TEST_URL_1 = "test_url_1";
    public static final String TEST_URL_2 = "test_url_2";
    public static final String MODIFIED_URL_1 = "modified_url_1";
    public static final String MODIFIED_URL_2 = "modified_url_2";

    @DisplayName("이미지 리스트를 받으면 images의 이미지 리스트로 수정한다")
    @Test
    void modify() {
        // given
        Images testImages = createTestImages();

        // when
        List<Image> modifiedImages = getModifiedImages();
        testImages.modify(modifiedImages);

        // then
        assertThat(testImages.getImageList()).usingRecursiveComparison().isEqualTo(modifiedImages);
    }

    @DisplayName("unmodifiable 이미지 리스틀 return한다")
    @Test
    void getImageList() {
        // given
        Images testImages = createTestImages();

        // when
        List<Image> imageList = testImages.getImageList();

        // then
        assertThat(imageList).isUnmodifiable();
    }

    private static Images createTestImages() {
        Image image1 = new Image(TEST_URL_1);
        Image image2 = new Image(TEST_URL_2);
        return new Images(List.of(image1, image2));
    }

    private static List<Image> getModifiedImages() {
        Image modifiedUrl1 = new Image(MODIFIED_URL_1);
        Image modifiedUrl2 = new Image(MODIFIED_URL_2);
        return List.of(modifiedUrl1, modifiedUrl2);
    }
}
