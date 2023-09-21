package com.codesquad.secondhand.adapter.out.persistence;

import com.codesquad.secondhand.adapter.out.persistence.imports.ProductCrudRepository;
import com.codesquad.secondhand.application.port.out.ProductRepository;
import com.codesquad.secondhand.domain.product.Product;
import com.codesquad.secondhand.domain.product.Status;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepository {

    private final ProductCrudRepository productCrudRepository;

    @Override
    public Product save(Product product) {
        return productCrudRepository.save(product);
    }

    @Override
    public Optional<Product> findById(Long id) {
        return productCrudRepository.findById(id);
    }

    @Override
    public Slice<Product> findLikesByMemberId(long memberId, Pageable pageable) {
        return productCrudRepository.findLikesByMemberId(memberId, pageable);
    }

    @Override
    public Slice<Product> findLikesByMemberIdAndCategoryId(long memberId, long categoryId, Pageable pageable) {
        return productCrudRepository.findLikesByMemberIdAndCategoryId(memberId, categoryId, pageable);
    }

    @Override
    public Slice<Product> findByWriterId(long writerId, Pageable pageable) {
        return productCrudRepository.findByWriterId(writerId, pageable);
    }

    @Override
    public Slice<Product> findByWriterIdAndStatus(long writerId, Status status, Pageable pageable) {
        return productCrudRepository.findByWriterIdAndStatus(writerId, status, pageable);
    }

    @Override
    public Slice<Product> findByWriterIdAndStatusNot(long writerId, Status status, Pageable pageable) {
        return productCrudRepository.findByWriterIdAndStatusNot(writerId, status, pageable);
    }

    @Override
    public Slice<Product> findByRegionId(long regionId, Pageable pageable) {
        return productCrudRepository.findByRegionId(regionId, pageable);
    }

    @Override
    public Slice<Product> findByRegionIdAndCategoryId(long regionId, long categoryId, Pageable pageable) {
        return productCrudRepository.findByRegionIdAndCategoryId(regionId, categoryId, pageable);
    }

    @Override
    public void deleteById(long productId) {
        productCrudRepository.deleteById(productId);
    }

    @Override
    public boolean existById(long productId) {
        return productCrudRepository.existsById(productId);
    }
}
