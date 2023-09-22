package com.codesquad.secondhand.command.service.in;

import com.codesquad.secondhand.command.domain.product.Product;
import com.codesquad.secondhand.command.port.out.ProductRepository;
import com.codesquad.secondhand.common.exception.InvalidEntityStateException;
import com.codesquad.secondhand.common.exception.ProductNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductCommandService {

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

    public void delete(long id) {
        productRepository.deleteById(id);
    }

    public Product getById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(ProductNotFoundException::new);
    }

    public void validateProductId(long productId) {
        if (productRepository.existById(productId)) {
            return;
        }
        throw new ProductNotFoundException();
    }
}
