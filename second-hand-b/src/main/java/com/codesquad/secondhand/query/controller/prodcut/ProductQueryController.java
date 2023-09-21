package com.codesquad.secondhand.query.controller.prodcut;

import com.codesquad.secondhand.query.controller.prodcut.response.ProductDetail;
import com.codesquad.secondhand.query.controller.prodcut.response.ProductsInfo;
import com.codesquad.secondhand.query.port.ProductQueryUseCase;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductQueryController {

    private final ProductQueryUseCase productQueryUseCase;

    @GetMapping("/{productId}")
    public ResponseEntity<ProductDetail> getDetails(@PathVariable long productId) {
        ProductDetail productDetail = productQueryUseCase.getDetails(productId);
        return ResponseEntity.ok()
                .body(productDetail);
    }

    @GetMapping
    public ResponseEntity<ProductsInfo> getProductList(
            @RequestParam long regionId
            , @RequestParam Optional<Long> categoryId, Pageable pageable) {
        if (categoryId.isPresent()) {
            ProductsInfo productsByRegionAndCategory = productQueryUseCase
                    .getProductsByRegionAndCategory(regionId, categoryId.get(), pageable);
            return ResponseEntity.ok()
                    .body(productsByRegionAndCategory);
        }
        ProductsInfo productsByRegion = productQueryUseCase.getProductsByRegion(regionId, pageable);
        return ResponseEntity.ok()
                .body(productsByRegion);
    }
}
