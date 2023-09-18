package com.codesquad.secondhand.adapter.out.persistence.imports;

import com.codesquad.secondhand.domain.product.Product;
import com.codesquad.secondhand.domain.product.Status;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ProductCrudRepository extends CrudRepository<Product, Long> {

    @Query(" select member_product from Member member_ "
            + " join member_.likes.products member_product"
            + " where member_.id = :memberId"
    )
    List<Product> findLikesByMemberId(long memberId);

    @Query(" select member_product from Member member_ "
            + " join member_.likes.products member_product"
            + " where member_.id = :memberId and member_product.categoryId = :categoryId"
    )
    List<Product> findLikesByMemberIdAndCategoryId(long memberId, long categoryId);

    List<Product> findByWriterId(long writerId);

    List<Product> findByWriterIdAndStatus(long writerId, Status status);

    List<Product> findByWriterIdAndStatusNot(long writerId, Status status);

    List<Product> findByRegionId(long regionId);

    List<Product> findByRegionIdAndCategoryId(long regionId, long categoryId);
}
