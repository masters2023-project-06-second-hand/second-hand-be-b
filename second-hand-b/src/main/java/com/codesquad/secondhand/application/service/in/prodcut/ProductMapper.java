package com.codesquad.secondhand.application.service.in.prodcut;

import static com.codesquad.secondhand.application.service.in.image.ImageMapper.toImageInfos;

import com.codesquad.secondhand.application.port.in.response.ImageInfo;
import com.codesquad.secondhand.application.port.in.response.ProductDetail;
import com.codesquad.secondhand.application.port.in.response.ProductInfo;
import com.codesquad.secondhand.application.port.in.response.ProductWriter;
import com.codesquad.secondhand.domain.image.Image;
import com.codesquad.secondhand.domain.member.Member;
import com.codesquad.secondhand.domain.product.Category;
import com.codesquad.secondhand.domain.product.Product;
import com.codesquad.secondhand.domain.product.Status;
import com.codesquad.secondhand.domain.region.Region;
import java.util.List;
import java.util.stream.Collectors;

public class ProductMapper {

    private ProductMapper() {
        throw new UnsupportedOperationException();
    }

    public static ProductInfo toProductInfo(Product product) {
        Member member = product.getWriter();
        Region region = product.getRegion();
        Status status = product.getStatus();
        String thumbnailUrl = product.getThumbnailUrl();
        return new ProductInfo(product.getId(),
                member.getId(),
                thumbnailUrl,
                product.getName(),
                region.getName(),
                product.getCreatedAt(),
                status.getName(),
                product.getPrice(),
                0,
                0);
    }

    public static List<ProductInfo> toProductsInfo(List<Product> products) {
        return products.stream()
                .map(ProductMapper::toProductInfo)
                .collect(Collectors.toList());
    }

    public static ProductDetail toProductDetail(Product product) {
        Member member = product.getWriter();
        Category category = product.getCategory();
        Region region = product.getRegion();
        Status status = product.getStatus();
        List<Image> images = product.fetchImages();
        List<ImageInfo> imageInfos = toImageInfos(images);
        return new ProductDetail(product.getId(),
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
