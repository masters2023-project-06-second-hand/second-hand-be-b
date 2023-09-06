package com.codesquad.secondhand.application.port.out;

import com.codesquad.secondhand.domain.product.Product;
import java.util.Optional;
import java.util.Set;

public interface ProductRepository {

    Product save(Product product);

    Optional<Product> findById(Long id);

    Set<Product> findProductsByMemberIdAndCategoryId(long memberId, long categoryId);

    Set<Product> findByWriterId(long writerId);
}
