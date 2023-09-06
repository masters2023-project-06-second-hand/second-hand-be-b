package com.codesquad.secondhand.adapter.out.persistence;

import com.codesquad.secondhand.adapter.out.persistence.imports.ProductJpaRepository;
import com.codesquad.secondhand.application.port.out.ProductRepository;
import com.codesquad.secondhand.domain.product.Product;
import com.codesquad.secondhand.domain.product.Status;
import java.util.Optional;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepository {

    private final ProductJpaRepository productJpaRepository;

    @Override
    public Product save(Product product) {
        return productJpaRepository.save(product);
    }

    @Override
    public Optional<Product> findById(Long id) {
        return productJpaRepository.findById(id);
    }

    @Override
    public Set<Product> findProductsByMemberIdAndCategoryId(long memberId, long categoryId) {
        return productJpaRepository.findProductsByMemberIdAndCategoryId(memberId, categoryId);
    }

    @Override
    public Set<Product> findByWriterId(long writerId) {
        return productJpaRepository.findByWriterId(writerId);
    }

    @Override
    public Set<Product> findByWriterIdAndStatus(long memberId, Status status) {
        return productJpaRepository.findByWriterIdAndStatus(memberId, status);
    }

    @Override
    public Set<Product> findByWriterIdAndStatusNot(long memberId, Status status) {
        return productJpaRepository.findByWriterIdAndStatusNot(memberId, status);
    }

    @Override
    public Set<Product> findByRegionId(long regionId) {
        return productJpaRepository.findByRegionId(regionId);
    }

    @Override
    public Set<Product> findByRegionIdAndCategoryId(long regionId, long categoryId) {
        return productJpaRepository.findByRegionIdAndCategoryId(regionId, categoryId);
    }
}
