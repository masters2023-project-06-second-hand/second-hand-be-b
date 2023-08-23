package com.codesquad.secondhand.application.port.in;

import com.codesquad.secondhand.domain.product.Product;

public interface ProductUseCase {

    Product save(Product product);
}
