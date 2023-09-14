package com.codesquad.secondhand.adapter.in.web;

import com.codesquad.secondhand.application.port.in.CategoryUseCase;
import com.codesquad.secondhand.adapter.in.web.response.CategoryDetail;
import com.codesquad.secondhand.adapter.in.web.response.CategorySimpleDetail;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/categories")
@RestController
public class CategoryController {

    private final CategoryUseCase categoryUseCase;

    @GetMapping
    public ResponseEntity<?> getCategories(@RequestParam boolean includeImages) {
        if (includeImages) {
            List<CategoryDetail> categoriesWithImgUrl = categoryUseCase.getCategoriesWithImgUrl();
            return ResponseEntity.ok()
                    .body(categoriesWithImgUrl);
        }
        List<CategorySimpleDetail> categories = categoryUseCase.getCategories();
        return ResponseEntity.ok()
                .body(categories);
    }
}
