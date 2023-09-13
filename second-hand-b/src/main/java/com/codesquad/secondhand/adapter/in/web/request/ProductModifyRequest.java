package com.codesquad.secondhand.adapter.in.web.request;

import java.util.List;
import lombok.Getter;

@Getter
public class ProductModifyRequest {

    private Long id;
    private String name;
    private String content;
    private int price;
    private Long categoryId;
    private Long regionId;
    private List<Long> imagesId;
}
