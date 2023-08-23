package com.codesquad.secondhand.application.port.in;

import com.codesquad.secondhand.application.port.in.request.ProductCreateRequest;

public interface ProductUseCase {

    Long save(ProductCreateRequest productCreateRequest, String email);
}
