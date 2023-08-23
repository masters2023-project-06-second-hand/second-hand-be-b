package com.codesquad.secondhand.adapter.in.web;

import com.codesquad.secondhand.application.port.in.ProductUseCase;
import com.codesquad.secondhand.application.port.in.request.ProductCreateRequest;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductUseCase productUseCase;

    @PostMapping("/products")
    public ResponseEntity<Map<String, Long>> create(@RequestBody ProductCreateRequest productCreateRequest) {
        Long id = productUseCase.save(productCreateRequest, null);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("id", id));
    }
}
