package com.codesquad.secondhand.command.service.in;

import com.codesquad.secondhand.command.port.out.CategoryRepository;
import com.codesquad.secondhand.common.exception.CategoryNotFoundException;
import com.codesquad.secondhand.command.domain.product.Category;
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
