package com.codesquad.secondhand.adapter.out.persistence.imports;

import com.codesquad.secondhand.domain.product.Product;
import com.codesquad.secondhand.domain.product.Status;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductJpaRepository extends JpaRepository<Product, Long> {

    @Query(
            " select member_product from Member member_ "
                    + " join member_.likes.products member_product"
                    + " where member_.id = :memberId and member_product.category.id = :categoryId"
    )
    Set<Product> findProductsByMemberIdAndCategoryId(long memberId, long categoryId);

    Set<Product> findByWriterId(long writerId);

    Set<Product> findByWriterIdAndStatus(long memberId, Status status);

    Set<Product> findByWriterIdAndStatusNot(long memberId, Status status);

    @Query("select product_ from Product product_"
            + " join fetch product_.thumbnailImage"
            + " join fetch product_.region")
    Set<Product> findByRegionId(long regionId);

    Set<Product> findByRegionIdAndCategoryId(long regionId, long categoryId);
}
