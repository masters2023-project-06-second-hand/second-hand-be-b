package com.codesquad.secondhand.adapter.out.persistence.imports;

import com.codesquad.secondhand.domain.product.Category;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CategoryJpaRepository extends JpaRepository<Category, Long> {

    @Query(
            "select category_ from Member member_ "
                    + "join member_.likes.products product_ "
                    + "join Category as category_ on category_.id = product_.categoryId "
                    + "where member_.id = :memberId"
    )
    List<Category> findCategoryByMemberId(long memberId);
}
