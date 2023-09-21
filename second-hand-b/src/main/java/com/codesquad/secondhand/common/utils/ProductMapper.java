package com.codesquad.secondhand.common.utils;

import static com.codesquad.secondhand.common.utils.ImageMapper.toImageInfos;

import com.codesquad.secondhand.command.adapter.in.web.image.response.ImageInfo;
import com.codesquad.secondhand.query.controller.prodcut.response.ProductDetail;
import com.codesquad.secondhand.query.controller.prodcut.response.ProductInfo;
import com.codesquad.secondhand.command.domain.product.Image;
import com.codesquad.secondhand.command.domain.member.Member;
import com.codesquad.secondhand.command.domain.product.Category;
import com.codesquad.secondhand.command.domain.product.Product;
import com.codesquad.secondhand.command.domain.product.Status;
import com.codesquad.secondhand.command.domain.region.Region;
import java.util.List;

public class ProductMapper {

    private ProductMapper() {
        throw new UnsupportedOperationException();
    }

    public static ProductInfo toProductInfo(Product product, Region region) {
        long writerId = product.getWriterId();
        Status status = product.getStatus();
        String thumbnailUrl = product.getThumbnailUrl();
        return new ProductInfo(product.getId(),
                writerId,
                thumbnailUrl,
                product.getName(),
                region.getName(),
                product.getCreatedAt(),
                status.getName(),
                product.getPrice(),
                0,
                0);
    }

    public static ProductDetail toProductDetail(Product product, Category category, Member member, Region region) {
        Status status = product.getStatus();
        List<Image> images = product.fetchImages();
        List<ImageInfo> imageInfos = toImageInfos(images);
        return new ProductDetail(
                product.getId(),
                new ProductWriter(member.getId(), member.getNickname()),
                product.getName(),
                category.getName(),
                region.getName(),
                status.getName(),
                product.getContent(),
                product.getPrice(),
                imageInfos,
                product.getCreatedAt());
    }
}
