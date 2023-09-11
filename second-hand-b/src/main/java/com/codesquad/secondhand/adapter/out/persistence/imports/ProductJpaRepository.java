package com.codesquad.secondhand.adapter.out.persistence.imports;

import com.codesquad.secondhand.domain.product.Product;
import com.codesquad.secondhand.domain.product.Status;
import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductJpaRepository extends JpaRepository<Product, Long> {

    @Query(
            " select member_product from Member member_ "
                    + " join member_.likes.products member_product"
                    + " where member_.id = :memberId and member_product.category.id = :categoryId"
    )
    List<Product> findProductsByMemberIdAndCategoryId(long memberId, long categoryId);

    List<Product> findByWriterId(long writerId);

    List<Product> findByWriterIdAndStatus(long memberId, Status status);

    List<Product> findByWriterIdAndStatusNot(long memberId, Status status);

    @EntityGraph(attributePaths = {"region"})
    List<Product> findByRegionId(long regionId);

    @EntityGraph(attributePaths = {"region"})
    List<Product> findByRegionIdAndCategoryId(long regionId, long categoryId);
}
