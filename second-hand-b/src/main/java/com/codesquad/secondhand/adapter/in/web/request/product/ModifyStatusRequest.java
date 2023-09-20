package com.codesquad.secondhand.adapter.in.web.request.product;

import javax.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class ModifyStatusRequest {
    @NotBlank
    private String status;

}
