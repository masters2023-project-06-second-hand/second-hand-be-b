package com.codesquad.secondhand.query.service;

import com.codesquad.secondhand.command.domain.product.Category;
import com.codesquad.secondhand.command.port.out.CategoryRepository;
import com.codesquad.secondhand.common.exception.CategoryNotFoundException;
import com.codesquad.secondhand.common.utils.MemberUtils;
import com.codesquad.secondhand.query.controller.prodcut.response.CategoryDetail;
import com.codesquad.secondhand.query.controller.prodcut.response.CategorySimpleDetail;
import com.codesquad.secondhand.query.port.CategoryQueryUseCase;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CategoryQueryService implements CategoryQueryUseCase {

    private final CategoryRepository categoryRepository;

    public Category getById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> {
                    throw new CategoryNotFoundException();
                });
    }

    public List<CategorySimpleDetail> getLikesCategoryByMemberId(String validatedMemberId, long memberId) {
        MemberUtils.validateMemberPermission(validatedMemberId, memberId);
        List<Category> categories = categoryRepository.findCategoryByMemberId(memberId);
        return toCategorySimpleDetail(categories);
    }

    @Override
    public List<CategoryDetail> getCategoriesWithImgUrl() {
        List<Category> categories = categoryRepository.findAll();
        return toCategoryDetailWithImg(categories);
    }

    @Override
    public List<CategorySimpleDetail> getCategories() {
        List<Category> categories = categoryRepository.findAll();
        return toCategorySimpleDetail(categories);
    }

    private List<CategorySimpleDetail> toCategorySimpleDetail(List<Category> categories) {
        return categories.stream()
                .map(category -> new CategorySimpleDetail(category.getId(), category.getName()))
                .collect(Collectors.toList());
    }

    private List<CategoryDetail> toCategoryDetailWithImg(List<Category> categories) {
        return categories.stream()
                .map(category -> new CategoryDetail(category.getId(), category.getName(), category.getImgUrl()))
                .collect(Collectors.toList());
    }
}
