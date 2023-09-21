package com.codesquad.secondhand.application.service.in.prodcut;

import com.codesquad.secondhand.application.port.out.ProductRepository;
import com.codesquad.secondhand.application.service.in.exception.InvalidEntityStateException;
import com.codesquad.secondhand.application.service.in.exception.ProductNotFoundException;
import com.codesquad.secondhand.domain.product.Product;
import com.codesquad.secondhand.domain.product.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public Product save(Product product) {
        Product savedProduct = productRepository.save(product);
        if (savedProduct.getId() == null) {
            throw new InvalidEntityStateException();
        }
        return savedProduct;
    }

    public void modifyStatus(long id, String status) {
        Product product = getById(id);
        product.modifyStatus(status);
    }

    public Slice<Product> getProductsByRegion(long regionId, Pageable pageable) {
        return productRepository.findByRegionId(regionId, pageable);
    }

    public Slice<Product> getProductsByRegionAndCategory(long regionId, long categoryId, Pageable pageable) {
        return productRepository.findByRegionIdAndCategoryId(regionId, categoryId, pageable);
    }

    public void delete(long id) {
        productRepository.deleteById(id);
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

    public void validateProductId(long productId) {
        if (productRepository.existById(productId)) {
            return;
        }
        throw new ProductNotFoundException();
    }
}
