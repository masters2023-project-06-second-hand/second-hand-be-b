package com.codesquad.secondhand.adapter.out.persistence;

import com.codesquad.secondhand.application.port.out.CategoryRepository;
import com.codesquad.secondhand.domain.product.Category;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CategoryRepositoryImpl implements CategoryRepository {

    private final CategoryJpaRepository categoryJpaRepository;

    @Override
    public void saveAll(List<Category> categories) {
        categoryJpaRepository.saveAll(categories);
    }
}
