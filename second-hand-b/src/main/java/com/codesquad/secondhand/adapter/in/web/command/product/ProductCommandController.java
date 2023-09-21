package com.codesquad.secondhand.adapter.in.web.command.product;

import com.codesquad.secondhand.adapter.in.web.command.product.request.ModifyStatusRequest;
import com.codesquad.secondhand.adapter.in.web.command.product.request.ProductCreateRequest;
import com.codesquad.secondhand.adapter.in.web.command.product.request.ProductModifyRequest;
import com.codesquad.secondhand.application.port.in.command.ProductCommandUseCase;
import java.util.Map;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductCommandController {

    private final ProductCommandUseCase productCommandUseCase;

    @PostMapping
    public ResponseEntity<Map<String, Long>> create(
            @AuthenticationPrincipal String validatedMemberId,
            @RequestBody @Valid ProductCreateRequest productCreateRequest
    ) {
        long id = productCommandUseCase.save(productCreateRequest, validatedMemberId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("id", id));
    }

    @PutMapping("/{productId}")
    public ResponseEntity<Void> modify(@PathVariable long productId,
            @RequestBody @Valid ProductModifyRequest productModifyRequest) {
        productCommandUseCase.modify(productId, productModifyRequest);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{productId}/status")
    public ResponseEntity<Void> modifyStatus(@PathVariable long productId,
            @RequestBody @Valid ModifyStatusRequest request) {
        productCommandUseCase.modifyStatus(productId, request.getStatus());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> delete(@PathVariable Long productId) {
        productCommandUseCase.delete(productId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
