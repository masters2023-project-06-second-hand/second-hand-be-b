package com.codesquad.secondhand.adapter.out.persistence;

import com.codesquad.secondhand.adapter.out.persistence.imports.ProductCrudRepository;
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
    public List<Product> findProductsByMemberId(long memberId) {
        return productCrudRepository.findProductsByMemberId(memberId);
    }

    @Override
    public List<Product> findProductsByMemberIdAndCategoryId(long memberId, long categoryId) {
        return productCrudRepository.findProductsByMemberIdAndCategoryId(memberId, categoryId);
    }

    @Override
    public List<Product> findByWriterId(long writerId) {
        return productCrudRepository.findByWriterId(writerId);
    }

    @Override
    public List<Product> findByWriterIdAndStatus(long writerId, Status status) {
        return productCrudRepository.findByWriterIdAndStatus(writerId, status);
    }

    @Override
    public List<Product> findByWriterIdAndStatusNot(long writerId, Status status) {
        return productCrudRepository.findByWriterIdAndStatusNot(writerId, status);
    }

    @Override
    public List<Product> findByRegionId(long regionId) {
        return productCrudRepository.findByRegionId(regionId);
    }

    @Override
    public List<Product> findByRegionIdAndCategoryId(long regionId, long categoryId) {
        return productCrudRepository.findByRegionIdAndCategoryId(regionId, categoryId);
    }

    @Override
    public void deleteById(long productId) {
        productCrudRepository.deleteById(productId);
    }
}
