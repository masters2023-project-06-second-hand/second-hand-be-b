package com.codesquad.secondhand.application.port.in.request;

import java.util.List;
import lombok.Getter;

@Getter
public class ProductCreateRequest {

    private String name;
    private String content;
    private int price;
    private Long categoryId;
    private Long regionId;
    private List<Long> imagesId;
}
