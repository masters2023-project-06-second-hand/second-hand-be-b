package com.codesquad.secondhand.adapter.in.web.request.product;

import com.codesquad.secondhand.adapter.in.web.request.annotation.CategoryId;
import com.codesquad.secondhand.adapter.in.web.request.annotation.ImagesId;
import com.codesquad.secondhand.adapter.in.web.request.annotation.ProductContent;
import com.codesquad.secondhand.adapter.in.web.request.annotation.ProductName;
import com.codesquad.secondhand.adapter.in.web.request.annotation.ProductPrice;
import com.codesquad.secondhand.adapter.in.web.request.annotation.RegionId;
import java.util.List;
import lombok.Getter;

@Getter
public class ProductModifyRequest {

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
}
