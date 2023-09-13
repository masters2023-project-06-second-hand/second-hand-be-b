package com.codesquad.secondhand.adapter.in.web.request;

import java.util.List;
import lombok.Getter;

@Getter
public class ProductCreateRequest {

    private String name;
    private String content;
    private int price;
    private long categoryId;
    private long regionId;
    private List<Long> imagesId;

    public ProductCreateRequest() {
    }

    public ProductCreateRequest(String name, String content, int price, Long categoryId, Long regionId,
            List<Long> imagesId) {
        this.name = name;
        this.content = content;
        this.price = price;
        this.categoryId = categoryId;
        this.regionId = regionId;
        this.imagesId = imagesId;
    }
}
