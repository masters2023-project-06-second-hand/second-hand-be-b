package com.codesquad.secondhand.adapter.out.persistence;

import com.codesquad.secondhand.adapter.out.persistence.imports.CategoryJpaRepository;
import com.codesquad.secondhand.application.port.out.CategoryRepository;
import com.codesquad.secondhand.domain.product.Category;
import java.util.List;
import java.util.Optional;
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

    @Override
    public Optional<Category> findById(Long id) {
        return categoryJpaRepository.findById(id);
    }

    @Override
    public List<Category> findCategoryByMemberId(long memberId) {
        return categoryJpaRepository.findCategoryByMemberId(memberId);
    }
}
