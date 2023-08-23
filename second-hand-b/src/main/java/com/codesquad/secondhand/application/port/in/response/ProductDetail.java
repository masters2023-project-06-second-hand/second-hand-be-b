package com.codesquad.secondhand.application.port.in.response;

import com.codesquad.secondhand.domain.image.Image;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;

@Getter
public class ProductDetail {

    private Long id;
    private String writer;
    private String productName;
    private String categoryName;
    private String region;
    private String status;
    private String content;
    private int price;
    private List<Image> images;
    private LocalDateTime createdAt;

    public ProductDetail(Long id, String writer, String productName, String categoryName, String region, String status,
            String content, int price, List<Image> images, LocalDateTime createdAt) {
        this.id = id;
        this.writer = writer;
        this.productName = productName;
        this.categoryName = categoryName;
        this.region = region;
        this.status = status;
        this.content = content;
        this.price = price;
        this.images = images;
        this.createdAt = createdAt;
    }
}
