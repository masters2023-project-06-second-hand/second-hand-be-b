package com.codesquad.secondhand.application.port.out;

import com.codesquad.secondhand.domain.image.Image;

public interface ImageRepository {

    Image save(Image image);
}
