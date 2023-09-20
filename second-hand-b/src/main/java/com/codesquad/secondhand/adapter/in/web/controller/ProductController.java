package com.codesquad.secondhand.adapter.in.web.controller;

import com.codesquad.secondhand.adapter.in.web.request.product.ModifyStatusRequest;
import com.codesquad.secondhand.adapter.in.web.request.product.ProductCreateRequest;
import com.codesquad.secondhand.adapter.in.web.request.product.ProductModifyRequest;
import com.codesquad.secondhand.adapter.in.web.response.product.ProductDetail;
import com.codesquad.secondhand.adapter.in.web.response.product.ProductsInfo;
import com.codesquad.secondhand.application.port.in.ProductUseCase;
import com.codesquad.secondhand.domain.member.Member;
import java.util.Map;
import java.util.Optional;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    private final ProductUseCase productUseCase;

    @PostMapping
    public ResponseEntity<Map<String, Long>> create(
            @AuthenticationPrincipal Member member,
            @RequestBody @Valid ProductCreateRequest productCreateRequest
    ) {
        long id = productUseCase.save(productCreateRequest, member);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("id", id));
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductDetail> getDetails(@PathVariable long productId) {
        ProductDetail productDetail = productUseCase.getDetails(productId);
        return ResponseEntity.ok()
                .body(productDetail);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<Void> modify(@PathVariable long productId,
            @RequestBody @Valid ProductModifyRequest productModifyRequest) {
        productUseCase.modify(productId, productModifyRequest);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{productId}/status")
    public ResponseEntity<Void> modifyStatus(@PathVariable long productId,
            @RequestBody @Valid ModifyStatusRequest request) {
        productUseCase.modifyStatus(productId, request.getStatus());
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<ProductsInfo> getProductList(
            @RequestParam long regionId
            , @RequestParam Optional<Long> categoryId, Pageable pageable) {
        if (categoryId.isPresent()) {
            ProductsInfo productsByRegionAndCategory = productUseCase
                    .getProductsByRegionAndCategory(regionId, categoryId.get(), pageable);
            return ResponseEntity.ok()
                    .body(productsByRegionAndCategory);
        }
        ProductsInfo productsByRegion = productUseCase.getProductsByRegion(regionId, pageable);
        return ResponseEntity.ok()
                .body(productsByRegion);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> delete(@PathVariable Long productId) {
        productUseCase.delete(productId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
