package com.codesquad.secondhand.adapter.out.persistence.imports;

import com.codesquad.secondhand.domain.product.Category;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CategoryJpaRepository extends JpaRepository<Category, Long> {

    @Query(
            "select category_ from Member member_ "
                    + "join member_.likes.products product_ "
                    + "join product_.category category_ "
                    + "where member_.id = :memberId"
    )
    List<Category> findCategoryByMemberId(@Param("memberId") long memberId);
}
