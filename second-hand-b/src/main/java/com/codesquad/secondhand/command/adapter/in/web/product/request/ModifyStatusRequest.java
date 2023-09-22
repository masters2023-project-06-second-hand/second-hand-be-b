package com.codesquad.secondhand.command.adapter.in.web.product.request;

import javax.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class ModifyStatusRequest {
    @NotBlank
    private String status;

}
