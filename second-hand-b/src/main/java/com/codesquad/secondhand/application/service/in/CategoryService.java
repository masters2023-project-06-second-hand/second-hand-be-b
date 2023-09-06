package com.codesquad.secondhand.application.service.in;

import com.codesquad.secondhand.application.port.in.exception.CategoryNotFoundException;
import com.codesquad.secondhand.application.port.in.response.CategorySimpleDetail;
import com.codesquad.secondhand.application.port.out.CategoryRepository;
import com.codesquad.secondhand.domain.product.Category;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public Category getById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> {
                    throw new CategoryNotFoundException();
                });
    }

    public List<CategorySimpleDetail> getCategoryByMemberId(long memberId) {
        List<Category> categories = categoryRepository.findCategoryByMemberId(memberId);
        return toCategorySimpleDetail(categories);
    }

    private List<CategorySimpleDetail> toCategorySimpleDetail(List<Category> categories) {
        return categories.stream()
                .map(category -> new CategorySimpleDetail(category.getId(), category.getName()))
                .collect(Collectors.toList());
    }
}
