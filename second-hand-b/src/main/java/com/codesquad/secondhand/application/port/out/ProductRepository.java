package com.codesquad.secondhand.application.port.out;

import com.codesquad.secondhand.domain.product.Product;
import java.util.Optional;

public interface ProductRepository {

    Product save(Product product);

    Optional<Product> findById(Long id);
}
