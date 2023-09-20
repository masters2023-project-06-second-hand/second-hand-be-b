package com.codesquad.secondhand.adapter.out.persistence.imports;

import com.codesquad.secondhand.domain.product.Category;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CategoryJpaRepository extends JpaRepository<Category, Long> {

    @Query(
            "select distinct category_ "
                    + "from Product product_ "
                    + "join product_.category category_ "
                    + "where product_.id in ( "
                    + " select likes_ "
                    + " from Member member_ "
                    + " join member_.likes.productsId likes_"
                    + " where member_.id = :memberId "
                    + ")"
    )
    List<Category> findCategoryByMemberId(long memberId);
}
