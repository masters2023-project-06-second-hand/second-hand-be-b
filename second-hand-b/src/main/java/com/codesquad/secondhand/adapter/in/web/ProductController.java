package com.codesquad.secondhand.adapter.in.web;

import com.codesquad.secondhand.application.port.in.ProductUseCase;
import com.codesquad.secondhand.application.port.in.request.ProductCreateRequest;
import com.codesquad.secondhand.application.port.in.request.ProductModifyRequest;
import com.codesquad.secondhand.application.port.in.response.ProductDetail;
import com.codesquad.secondhand.application.port.in.response.ProductInfo;
import com.codesquad.secondhand.domain.member.Member;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
            @RequestBody ProductCreateRequest productCreateRequest
    ) {
        Long id = productUseCase.save(productCreateRequest, member);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("id", id));
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductDetail> getDetails(@PathVariable Long productId) {
        ProductDetail productDetail = productUseCase.getDetails(productId);
        return ResponseEntity.ok()
                .body(productDetail);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<Void> modify(@PathVariable Long productId,
            @RequestBody ProductModifyRequest productModifyRequest) {
        productUseCase.modify(productId, productModifyRequest);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{productId}/status")
    public ResponseEntity<Void> modifyStatus(@PathVariable Long productId,
            @RequestBody Map<String, String> request) {
        productUseCase.modifyStatus(productId, request.get("status"));
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<ProductInfo>> getProductList(
            @RequestParam Long regionId
            , @RequestParam Optional<Long> categoryId) {
        if (categoryId.isPresent()) {
            List<ProductInfo> productsByRegionAndCategory = productUseCase.getProductsByRegionAndCategory(regionId,
                    categoryId.get());
            return ResponseEntity.ok()
                    .body(productsByRegionAndCategory);
        }
        List<ProductInfo> productsByRegion = productUseCase.getProductsByRegion(regionId);
        return ResponseEntity.ok()
                .body(productsByRegion);
    }
}
