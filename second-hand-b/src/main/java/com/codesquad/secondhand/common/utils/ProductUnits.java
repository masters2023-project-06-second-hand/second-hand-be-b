package com.codesquad.secondhand.common.utils;

import com.codesquad.secondhand.command.domain.product.Image;
import com.codesquad.secondhand.common.exception.MinimumImageRequirementException;
import java.util.List;

public class ProductUnits {

    private static final int IMAGES_FIRST_INDEX = 0;

    private ProductUnits() {
        throw new UnsupportedOperationException();
    }

    public static String getThumbnailUrl(List<Image> images) {
        if (images.isEmpty()) {
            throw new MinimumImageRequirementException();
        }
        return images.get(IMAGES_FIRST_INDEX).getUrl();
    }

}
