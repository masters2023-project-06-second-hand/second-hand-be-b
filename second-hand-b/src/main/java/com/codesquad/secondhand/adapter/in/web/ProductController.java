package com.codesquad.secondhand.adapter.in.web;

import com.codesquad.secondhand.application.port.in.ProductUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductUseCase productUseCase;

}
