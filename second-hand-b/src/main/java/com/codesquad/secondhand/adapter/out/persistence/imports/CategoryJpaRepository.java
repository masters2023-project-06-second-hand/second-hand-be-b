package com.codesquad.secondhand.adapter.out.persistence.imports;

import com.codesquad.secondhand.domain.product.Category;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CategoryJpaRepository extends JpaRepository<Category, Long> {

    @Query(
            "select category_ from Category category_ "
                    + "where category_.id "
                    + "in ("
                    + "select product_.categoryId "
                    + "from Member member_ "
                    + "join member_.likes.products product_ "
                    + "where member_.id = :memberId"
                    + ")"
    )
    List<Category> findCategoryByMemberId(long memberId);
}
