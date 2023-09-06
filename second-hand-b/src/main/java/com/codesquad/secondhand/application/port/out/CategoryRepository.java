package com.codesquad.secondhand.application.port.out;

import com.codesquad.secondhand.domain.product.Category;
import java.util.List;
import java.util.Optional;

public interface CategoryRepository {

    void saveAll(List<Category> categories);

    Optional<Category> findById(Long id);

    List<Category> findCategoryByMemberId(long memberId);

    List<Category> findAll();
}
