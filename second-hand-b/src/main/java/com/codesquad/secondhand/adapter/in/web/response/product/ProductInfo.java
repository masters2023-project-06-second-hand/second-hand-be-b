package com.codesquad.secondhand.adapter.in.web.response.product;

import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class ProductInfo {

    private Long id;
    private Long writerId;
    private String thumbnailUrl;
    private String name;
    private String region;
    private LocalDateTime createdAt;
    private String status;
    private int price;
    private int likeCount;
    private int chattingCount;

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
