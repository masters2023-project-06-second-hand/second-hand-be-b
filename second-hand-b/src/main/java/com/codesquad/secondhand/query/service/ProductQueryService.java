package com.codesquad.secondhand.query.service;

import com.codesquad.secondhand.command.domain.product.Product;
import com.codesquad.secondhand.command.domain.product.Status;
import com.codesquad.secondhand.command.port.out.ProductRepository;
import com.codesquad.secondhand.common.exception.ProductNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductQueryService {

    private final ProductRepository productRepository;

    public Slice<Product> getProductsByRegion(long regionId, Pageable pageable) {
        return productRepository.findByRegionId(regionId, pageable);
    }

    public Slice<Product> getProductsByRegionAndCategory(long regionId, long categoryId, Pageable pageable) {
        return productRepository.findByRegionIdAndCategoryId(regionId, categoryId, pageable);
    }


    public Product getById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(ProductNotFoundException::new);
    }

    public Slice<Product> getLikesByMemberId(long memberId, Pageable pageable) {
        return productRepository.findLikesByMemberId(memberId, pageable);
    }

    public Slice<Product> getLikesByMemberIdAndCategoryId(long memberId, long categoryId, Pageable pageable) {
        return productRepository.findLikesByMemberIdAndCategoryId(
                memberId,
                categoryId,
                pageable
        );
    }

    public Slice<Product> getByWriterId(long memberId, Pageable pageable) {
        return productRepository.findByWriterId(memberId, pageable);
    }

    public Slice<Product> getSoldOutByWriterId(long memberId, Pageable pageable) {
        return productRepository.findByWriterIdAndStatus(memberId, Status.SOLD_OUT, pageable);
    }

    public Slice<Product> getSalesByWriterId(long memberId, Pageable pageable) {
        return productRepository.findByWriterIdAndStatusNot(memberId, Status.SOLD_OUT, pageable);
    }
}
