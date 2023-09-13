package com.codesquad.secondhand.adapter.in.web.request;

import java.util.List;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;

@Getter
public class ProductModifyRequest {
    @Min(value = 0)
    private long id;
    @NotBlank
    @Size(min = 3, max = 50)
    private String name;
    @NotBlank
    @Size(min = 3, max = 1000)
    private String content;
    @Min(value = 0)
    private int price;
    @Min(value = 0)
    private long categoryId;
    @Min(value = 0)
    private long regionId;
    @NotNull
    @Size(min = 1)
    private List<Long> imagesId;
}
