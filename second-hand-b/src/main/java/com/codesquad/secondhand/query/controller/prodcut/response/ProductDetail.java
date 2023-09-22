package com.codesquad.secondhand.query.controller.prodcut.response;

import com.codesquad.secondhand.command.adapter.in.web.image.response.ImageInfo;
import com.codesquad.secondhand.common.utils.ProductWriter;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;

@Getter
public class ProductDetail {

    private final Long id;
    private final ProductWriter writer;
    private final String productName;
    private final String categoryName;
    private final String regionName;
    private final String status;
    private final String content;
    private final int price;
    private final List<ImageInfo> images;
    private final LocalDateTime createdAt;

    public ProductDetail(Long id, ProductWriter writer, String productName, String categoryName, String regionName,
            String status, String content, int price, List<ImageInfo> images, LocalDateTime createdAt) {
        this.id = id;
        this.writer = writer;
        this.productName = productName;
        this.categoryName = categoryName;
        this.regionName = regionName;
        this.status = status;
        this.content = content;
        this.price = price;
        this.images = images;
        this.createdAt = createdAt;
    }
}
