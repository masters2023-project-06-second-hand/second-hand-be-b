package com.codesquad.secondhand.application.service.in.image;

import com.codesquad.secondhand.application.port.in.response.ImageInfo;
import com.codesquad.secondhand.domain.image.Image;

public class ImageToImageInfoMapper {

    private ImageToImageInfoMapper() {
        throw new UnsupportedOperationException();
    }

    public static ImageInfo map(Image image) {
        return new ImageInfo(image.getId(), image.getUrl());
    }

}
