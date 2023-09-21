package com.codesquad.secondhand.adapter.in.web.command.product.request;

import javax.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class ModifyStatusRequest {
    @NotBlank
    private String status;

}
