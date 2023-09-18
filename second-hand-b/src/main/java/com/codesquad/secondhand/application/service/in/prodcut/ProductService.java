package com.codesquad.secondhand.application.service.in.prodcut;

import static com.codesquad.secondhand.application.service.in.prodcut.ProductMapper.toProductsInfo;

import com.codesquad.secondhand.adapter.in.web.response.ProductInfo;
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

    public List<ProductInfo> getProductsByRegion(long regionId) {
        List<Product> products = productRepository.findByRegionId(regionId);
        return toProductsInfo(products);
    }

    public List<ProductInfo> getProductsByRegionAndCategory(long regionId, long categoryId) {
        List<Product> products = productRepository.findByRegionIdAndCategoryId(regionId, categoryId);
        return toProductsInfo(products);
    }

    public void delete(long id) {
        productRepository.deleteById(id);
    }

    public Product getById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(ProductNotFoundException::new);
    }

    public List<ProductInfo> getProductsByMemberId(long memberId) {
        List<Product> products = productRepository.findProductsByMemberId(memberId);
        return toProductsInfo(products);
    }

    public List<ProductInfo> getProductsByMemberIdAndCategoryId(long memberId, long categoryId) {
        List<Product> products = productRepository.findProductsByMemberIdAndCategoryId(
                memberId,
                categoryId
        );
        return toProductsInfo(products);
    }

    public List<ProductInfo> getByWriterId(long memberId) {
        List<Product> products = productRepository.findByWriterId(memberId);
        return toProductsInfo(products);
    }

    public List<ProductInfo> getSoldOutByWriterId(long memberId) {
        List<Product> products = productRepository.findByWriterIdAndStatus(memberId, Status.SOLD_OUT);
        return toProductsInfo(products);
    }

    public List<ProductInfo> getSalesByWriterId(long memberId) {
        List<Product> products = productRepository.findByWriterIdAndStatusNot(memberId, Status.SOLD_OUT);
        return toProductsInfo(products);
    }
}
