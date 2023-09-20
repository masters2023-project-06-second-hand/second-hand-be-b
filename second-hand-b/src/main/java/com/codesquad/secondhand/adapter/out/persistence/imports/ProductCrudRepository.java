package com.codesquad.secondhand.adapter.out.persistence.imports;

import com.codesquad.secondhand.domain.product.Product;
import com.codesquad.secondhand.domain.product.Status;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductCrudRepository extends JpaRepository<Product, Long> {

    @Query(" select member_product from Member member_ "
            + " join member_.likes.products member_product"
            + " where member_.id = :memberId "
    )
    Slice<Product> findLikesByMemberId(long memberId, Pageable pageable);

    @Query(value = " select member_product from Member member_ "
            + " join member_.likes.products member_product"
            + " where member_.id = :memberId and member_product.categoryId = :categoryId"
    )
    Slice<Product> findLikesByMemberIdAndCategoryId(long memberId, long categoryId, Pageable pageable);

    Slice<Product> findByWriterId(long writerId, Pageable pageable);

    Slice<Product> findByWriterIdAndStatus(long writerId, Status status, Pageable pageable);

    Slice<Product> findByWriterIdAndStatusNot(long writerId, Status status, Pageable pageable);

    Slice<Product> findByRegionId(long regionId, Pageable pageable);

    Slice<Product> findByRegionIdAndCategoryId(long regionId, long categoryId, Pageable pageable);
}
