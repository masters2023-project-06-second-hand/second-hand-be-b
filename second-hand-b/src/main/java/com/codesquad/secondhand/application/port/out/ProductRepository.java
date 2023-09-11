package com.codesquad.secondhand.application.port.out;

import com.codesquad.secondhand.domain.product.Product;
import com.codesquad.secondhand.domain.product.Status;
import java.util.List;
import java.util.Optional;

public interface ProductRepository {

    Product save(Product product);

    Optional<Product> findById(Long id);

    List<Product> findProductsByMemberId(long memberId);

    List<Product> findProductsByMemberIdAndCategoryId(long memberId, long categoryId);

    List<Product> findByWriterId(long writerId);

    List<Product> findByWriterIdAndStatus(long memberId, Status status);

    List<Product> findByWriterIdAndStatusNot(long memberId, Status status);

    List<Product> findByRegionId(long regionId);

    List<Product> findByRegionIdAndCategoryId(long regionId, long categoryId);
}
