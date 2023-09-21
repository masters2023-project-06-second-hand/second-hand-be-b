package com.codesquad.secondhand.query.controller.prodcut.response;

import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class ProductInfo {

    private final Long id;
    private final Long writerId;
    private final String thumbnailUrl;
    private final String name;
    private final String region;
    private final LocalDateTime createdAt;
    private final String status;
    private final int price;
    private final int likeCount;
    private final int chattingCount;

    public ProductInfo(Long id, Long writerId, String thumbnailUrl, String name, String region, LocalDateTime createdAt,
            String status, int price, int likeCount, int chattingCount) {
        this.id = id;
        this.writerId = writerId;
        this.thumbnailUrl = thumbnailUrl;
        this.name = name;
        this.region = region;
        this.createdAt = createdAt;
        this.status = status;
        this.price = price;
        this.likeCount = likeCount;
        this.chattingCount = chattingCount;
    }
}
