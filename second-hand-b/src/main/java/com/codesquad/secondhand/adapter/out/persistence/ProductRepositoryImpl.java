package com.codesquad.secondhand.adapter.out.persistence;

import com.codesquad.secondhand.adapter.out.persistence.imports.ProductJpaRepository;
import com.codesquad.secondhand.application.port.out.ProductRepository;
import com.codesquad.secondhand.domain.product.Product;
import com.codesquad.secondhand.domain.product.Status;
import java.util.List;
import java.util.Optional;
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
    public List<Product> findProductsByMemberId(long memberId) {
        return productJpaRepository.findProductsByMemberId(memberId);
    }

    @Override
    public List<Product> findProductsByMemberIdAndCategoryId(long memberId, long categoryId) {
        return productJpaRepository.findProductsByMemberIdAndCategoryId(memberId, categoryId);
    }

    @Override
    public List<Product> findByWriterId(long writerId) {
        return productJpaRepository.findByWriterId(writerId);
    }

    @Override
    public List<Product> findByWriterIdAndStatus(long memberId, Status status) {
        return productJpaRepository.findByWriterIdAndStatus(memberId, status);
    }

    @Override
    public List<Product> findByWriterIdAndStatusNot(long memberId, Status status) {
        return productJpaRepository.findByWriterIdAndStatusNot(memberId, status);
    }

    @Override
    public List<Product> findByRegionId(long regionId) {
        return productJpaRepository.findByRegionId(regionId);
    }

    @Override
    public List<Product> findByRegionIdAndCategoryId(long regionId, long categoryId) {
        return productJpaRepository.findByRegionIdAndCategoryId(regionId, categoryId);
    }
}
