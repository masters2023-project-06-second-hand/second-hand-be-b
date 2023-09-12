package com.codesquad.secondhand.application.service.in.prodcut;

import com.codesquad.secondhand.application.port.in.exception.MinimumImageRequirementException;
import com.codesquad.secondhand.domain.image.Image;
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
