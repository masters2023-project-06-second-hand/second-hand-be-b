package com.codesquad.secondhand.application.port.in.response;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;

@Getter
public class ProductDetail {

    private Long id;
    private ProductWriter writer;
    private String productName;
    private String categoryName;
    private String regionName;
    private String status;
    private String content;
    private int price;
    private List<ImageInfo> images;
    private LocalDateTime createdAt;

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
