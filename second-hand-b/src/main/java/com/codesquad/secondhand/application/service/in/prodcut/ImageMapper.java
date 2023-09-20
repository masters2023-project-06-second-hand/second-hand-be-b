package com.codesquad.secondhand.application.service.in.prodcut;

import com.codesquad.secondhand.adapter.in.web.response.product.ImageInfo;
import com.codesquad.secondhand.domain.product.Image;
import java.util.List;
import java.util.stream.Collectors;

public class ImageMapper {

    private ImageMapper() {
        throw new UnsupportedOperationException();
    }

    public static ImageInfo toImageInfo(Image image) {
        return new ImageInfo(image.getId(), image.getUrl());
    }

    public static List<ImageInfo> toImageInfos(List<Image> images) {
        return images.stream()
                .map(ImageMapper::toImageInfo)
                .collect(Collectors.toUnmodifiableList());
    }
}
