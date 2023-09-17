package com.codesquad.secondhand.adapter.in.web.request;

import com.codesquad.secondhand.adapter.in.web.request.annotation.CategoryId;
import com.codesquad.secondhand.adapter.in.web.request.annotation.ImagesId;
import com.codesquad.secondhand.adapter.in.web.request.annotation.ProductContent;
import com.codesquad.secondhand.adapter.in.web.request.annotation.ProductName;
import com.codesquad.secondhand.adapter.in.web.request.annotation.ProductPrice;
import com.codesquad.secondhand.adapter.in.web.request.annotation.RegionId;
import java.util.List;
import lombok.Getter;

@Getter
public class ProductCreateRequest {

    @ProductName
    private String name;
    @ProductContent
    private String content;
    @ProductPrice
    private int price;
    @CategoryId
    private long categoryId;
    @RegionId
    private long regionId;
    @ImagesId
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
