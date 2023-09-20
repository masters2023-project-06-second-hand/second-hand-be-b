package com.codesquad.secondhand.application.port.out;

import com.codesquad.secondhand.domain.product.Product;
import com.codesquad.secondhand.domain.product.Status;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface ProductRepository {

    Product save(Product product);

    Optional<Product> findById(Long id);

    Slice<Product> findLikesByMemberId(long memberId, Pageable pageable);

    Slice<Product> findLikesByMemberIdAndCategoryId(long memberId, long categoryId, Pageable pageable);

    Slice<Product> findByWriterId(long writerId, Pageable pageable);

    Slice<Product> findByWriterIdAndStatus(long writerId, Status status, Pageable pageable);

    Slice<Product> findByWriterIdAndStatusNot(long writerId, Status status, Pageable pageable);

    Slice<Product> findByRegionId(long regionId, Pageable pageable);

    Slice<Product> findByRegionIdAndCategoryId(long regionId, long categoryId, Pageable pageable);

    void deleteById(long productId);

    boolean existById(long productId);
}
