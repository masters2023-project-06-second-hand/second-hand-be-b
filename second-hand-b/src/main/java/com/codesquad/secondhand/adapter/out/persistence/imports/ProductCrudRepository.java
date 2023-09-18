package com.codesquad.secondhand.adapter.out.persistence.imports;

import com.codesquad.secondhand.domain.product.Product;
import com.codesquad.secondhand.domain.product.Status;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ProductCrudRepository extends CrudRepository<Product, Long> {

    @Query(
            " select member_product from Member member_ "
                    + " join member_.likes.products member_product"
                    + " join fetch member_product.region"
                    + " where member_.id = :memberId"
    )
    List<Product> findProductsByMemberId(long memberId);

    @Query(
            " select member_product from Member member_ "
                    + " join member_.likes.products member_product"
                    + " join fetch member_product.region"
                    + " where member_.id = :memberId and member_product.category.id = :categoryId"
    )
    List<Product> findProductsByMemberIdAndCategoryId(long memberId, long categoryId);

    @Query(
            "select product_ from Product product_"
                    + " join fetch product_.region"
                    + " where product_.writerId = :writerId"
    )
    List<Product> findByWriterId(long writerId);

    @Query(
            "select product_ from Product product_"
                    + " join fetch product_.region"
                    + " where product_.writerId = :writerId and product_.status = :status"
    )
    List<Product> findByWriterIdAndStatus(long writerId, Status status);

    @Query(
            "select product_ from Product product_"
                    + " join fetch product_.region"
                    + " where product_.writerId = :writerId and product_.status != :status"
    )
    List<Product> findByWriterIdAndStatusNot(long writerId, Status status);

    @Query(
            "select product_ from Product product_"
                    + " join fetch product_.region region_"
                    + " where region_.id = :regionId"
    )
    List<Product> findByRegionId(long regionId);

    @Query(
            "select product_ from Product product_"
                    + " join fetch product_.region region_"
                    + " where region_.id = :regionId and product_.category.id = :categoryId"
    )
    List<Product> findByRegionIdAndCategoryId(long regionId, long categoryId);
}
