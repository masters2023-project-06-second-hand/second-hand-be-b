package com.codesquad.secondhand.adapter.in.web.command.product.request;

import com.codesquad.secondhand.adapter.in.web.command.product.annotation.CategoryId;
import com.codesquad.secondhand.adapter.in.web.command.product.annotation.ImagesId;
import com.codesquad.secondhand.adapter.in.web.command.product.annotation.ProductContent;
import com.codesquad.secondhand.adapter.in.web.command.product.annotation.ProductName;
import com.codesquad.secondhand.adapter.in.web.command.product.annotation.ProductPrice;
import com.codesquad.secondhand.adapter.in.web.command.product.annotation.RegionId;
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
