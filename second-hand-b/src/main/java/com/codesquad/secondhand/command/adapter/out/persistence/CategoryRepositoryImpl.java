package com.codesquad.secondhand.command.adapter.out.persistence;

import com.codesquad.secondhand.command.adapter.out.persistence.imports.CategoryJpaRepository;
import com.codesquad.secondhand.command.domain.product.Category;
import com.codesquad.secondhand.command.port.out.CategoryRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CategoryRepositoryImpl implements CategoryRepository {

    private final CategoryJpaRepository categoryJpaRepository;

    @Override
    public Optional<Category> findById(Long id) {
        return categoryJpaRepository.findById(id);
    }

    @Override
    public List<Category> findCategoryByMemberId(long memberId) {
        return categoryJpaRepository.findCategoryByMemberId(memberId);
    }

    @Override
    public List<Category> findAll() {
        return categoryJpaRepository.findAll();
    }
}
