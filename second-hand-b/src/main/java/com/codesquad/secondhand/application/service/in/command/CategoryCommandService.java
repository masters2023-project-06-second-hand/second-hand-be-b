package com.codesquad.secondhand.application.service.in.command;

import com.codesquad.secondhand.application.port.out.CategoryRepository;
import com.codesquad.secondhand.application.service.in.common.exception.CategoryNotFoundException;
import com.codesquad.secondhand.domain.product.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CategoryCommandService {

    private final CategoryRepository categoryRepository;

    public Category getById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> {
                    throw new CategoryNotFoundException();
                });
    }
}
