package com.codesquad.secondhand.adapter.in.web.response.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class ProductsInfo {

    @JsonProperty(value = "products")
    List<ProductInfo> productInfos;
    boolean hasNext;
    int page;

    public ProductsInfo(List<ProductInfo> productInfos, boolean hasNext, int page) {
        this.productInfos = productInfos;
        this.hasNext = hasNext;
        this.page = page;
    }

    public List<ProductInfo> getProductInfos() {
        return productInfos;
    }

    public boolean isHasNext() {
        return hasNext;
    }

    public int getPage() {
        return page;
    }
}
