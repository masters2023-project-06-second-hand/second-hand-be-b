package com.codesquad.secondhand.application.port.out;

import com.codesquad.secondhand.domain.product.Product;

public interface ProductRepository {

    Product save(Product product);
}
