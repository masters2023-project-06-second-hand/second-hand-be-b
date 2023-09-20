package com.codesquad.secondhand.application.service.in.prodcut;

import com.codesquad.secondhand.application.port.out.ProductRepository;
import com.codesquad.secondhand.application.service.in.exception.InvalidEntityStateException;
import com.codesquad.secondhand.application.service.in.exception.ProductNotFoundException;
import com.codesquad.secondhand.domain.product.Product;
import com.codesquad.secondhand.domain.product.Status;
import java.util.List;
import lombok.RequiredArgsConstructor;
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

    public List<Product> getProductsByRegion(long regionId) {
        return productRepository.findByRegionId(regionId);
    }

    public List<Product> getProductsByRegionAndCategory(long regionId, long categoryId) {
        return productRepository.findByRegionIdAndCategoryId(regionId, categoryId);
    }

    public void delete(long id) {
        productRepository.deleteById(id);
    }

    public Product getById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(ProductNotFoundException::new);
    }

    public List<Product> getLikesByMemberId(long memberId) {
        return productRepository.findLikesByMemberId(memberId);
    }

    public List<Product> getLikesByMemberIdAndCategoryId(long memberId, long categoryId) {
        return productRepository.findLikesByMemberIdAndCategoryId(
                memberId,
                categoryId
        );
    }

    public List<Product> getByWriterId(long memberId) {
        return productRepository.findByWriterId(memberId);
    }

    public List<Product> getSoldOutByWriterId(long memberId) {
        return productRepository.findByWriterIdAndStatus(memberId, Status.SOLD_OUT);
    }

    public List<Product> getSalesByWriterId(long memberId) {
        return productRepository.findByWriterIdAndStatusNot(memberId, Status.SOLD_OUT);
    }
}
