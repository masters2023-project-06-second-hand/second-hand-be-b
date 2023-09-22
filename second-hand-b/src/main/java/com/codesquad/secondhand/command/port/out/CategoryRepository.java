package com.codesquad.secondhand.command.port.out;

import com.codesquad.secondhand.command.domain.product.Category;
import java.util.List;
import java.util.Optional;

public interface CategoryRepository {

    Optional<Category> findById(Long id);

    List<Category> findCategoryByMemberId(long memberId);

    List<Category> findAll();
}
