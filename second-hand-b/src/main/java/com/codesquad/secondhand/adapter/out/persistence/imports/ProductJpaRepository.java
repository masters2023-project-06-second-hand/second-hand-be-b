package com.codesquad.secondhand.adapter.out.persistence.imports;

import com.codesquad.secondhand.domain.product.Product;
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
}
