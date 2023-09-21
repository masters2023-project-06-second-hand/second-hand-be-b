package com.codesquad.secondhand.adapter.in.web.query.prodcut;

import com.codesquad.secondhand.adapter.in.web.query.prodcut.response.CategoryDetail;
import com.codesquad.secondhand.adapter.in.web.query.prodcut.response.CategorySimpleDetail;
import com.codesquad.secondhand.application.port.in.query.CategoryQueryUseCase;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/categories")
@RestController
public class CategoryQueryController {

    private final CategoryQueryUseCase categoryQueryUseCase;

    @GetMapping(params = "includeImages=true")
    public ResponseEntity<List<CategoryDetail>> getCategories() {
        List<CategoryDetail> categoriesWithImgUrl = categoryQueryUseCase.getCategoriesWithImgUrl();
        return ResponseEntity.ok(categoriesWithImgUrl);
    }

    @GetMapping
    public ResponseEntity<List<CategorySimpleDetail>> getSimpleCategories() {
        List<CategorySimpleDetail> categories = categoryQueryUseCase.getCategories();
        return ResponseEntity.ok().body(categories);
    }
}
